import { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const ENDPOINTS = [
  {
    category: 'Books',
    items: [
      { name: 'Get All Books', method: 'GET', url: '/api/books', desc: 'Retrieve a list of all books.' },
      { name: 'Add Book', method: 'POST', url: '/api/books', body: { title: "", price: 0.0, publicationYear: 2023, authorId: "author_id_here", isbn: "ISBN-000", stockQuantity: 10 }, desc: 'Add a new book.' },
      { name: 'Get Book by ID', method: 'GET', url: '/api/books/{id}', params: ['id'], desc: 'Retrieve a specific book.' },
      { name: 'Update Book', method: 'PUT', url: '/api/books/{id}', params: ['id'], body: { title: "", price: 0.0, publicationYear: 2023, authorId: "author_id_here", isbn: "ISBN-000", stockQuantity: 10 }, desc: 'Update details of a book.' },
      { name: 'Delete Book', method: 'DELETE', url: '/api/books/{id}', params: ['id'], desc: 'Remove a book.' }
    ]
  },
  {
    category: 'Authors',
    items: [
      { name: 'Get All Authors', method: 'GET', url: '/api/authors', desc: 'Retrieve a list of all authors.' },
      { name: 'Add Author', method: 'POST', url: '/api/authors', body: { name: "", email: "author@example.com" }, desc: 'Register a new author.' },
      { name: 'Get Author by ID', method: 'GET', url: '/api/authors/{id}', params: ['id'], desc: 'Retrieve specific author details.' },
      { name: 'Update Author', method: 'PUT', url: '/api/authors/{id}', params: ['id'], body: { name: "Updated Name", email: "updated@example.com" }, desc: 'Update author details.' },
      { name: 'Delete Author', method: 'DELETE', url: '/api/authors/{id}', params: ['id'], desc: 'Remove an author.' },
      { name: 'Get Author Books', method: 'GET', url: '/api/authors/{id}/books', params: ['id'], desc: 'Get all books using author ID.' }
    ]
  },
  {
    category: 'Customers',
    items: [
      { name: 'Get All Customers', method: 'GET', url: '/api/customers', desc: 'Retrieve a list of all customers.' },
      { name: 'Add Customer', method: 'POST', url: '/api/customers', body: { name: "", email: "user@example.com", password: "password123" }, desc: 'Register a new customer.' },
      { name: 'Get Customer by ID', method: 'GET', url: '/api/customers/{id}', params: ['id'], desc: 'Retrieve specific customer details.' },
      { name: 'Update Customer', method: 'PUT', url: '/api/customers/{id}', params: ['id'], body: { name: "", email: "user@example.com", password: "password123" }, desc: 'Update customer details.' },
      { name: 'Delete Customer', method: 'DELETE', url: '/api/customers/{id}', params: ['id'], desc: 'Remove a customer.' }
    ]
  },
  {
    category: 'Cart',
    items: [
      { name: 'Get Cart', method: 'GET', url: '/api/customers/{customerId}/cart', params: ['customerId'], desc: 'Retrieve customer cart.' },
      { name: 'Add Item to Cart', method: 'POST', url: '/api/customers/{customerId}/cart/items', params: ['customerId'], body: { bookId: "book_id", quantity: 1 }, desc: 'Add a book to the cart.' },
      { name: 'Update Cart Item', method: 'PUT', url: '/api/customers/{customerId}/cart/items/{bookId}', params: ['customerId', 'bookId'], body: { quantity: 2 }, desc: 'Update quantity of a book in cart.' },
      { name: 'Remove Cart Item', method: 'DELETE', url: '/api/customers/{customerId}/cart/items/{bookId}', params: ['customerId', 'bookId'], desc: 'Remove a book from the cart.' }
    ]
  },
  {
    category: 'Orders',
    items: [
      { name: 'Get All Orders', method: 'GET', url: '/api/customers/{customerId}/orders', params: ['customerId'], desc: 'Retrieve all orders for a customer.' },
      { name: 'Place Order', method: 'POST', url: '/api/customers/{customerId}/orders', params: ['customerId'], desc: 'Place a new order for the items in cart.' },
      { name: 'Get Order by ID', method: 'GET', url: '/api/customers/{customerId}/orders/{orderId}', params: ['customerId', 'orderId'], desc: 'Retrieve specific order details.' }
    ]
  }
];

function App() {
  const [selectedEndpoint, setSelectedEndpoint] = useState(null);

  // Request State
  const [baseUrl] = useState('http://localhost:8080'); // Hardcoded backend base URL
  const [pathParams, setPathParams] = useState({});
  const [body, setBody] = useState('');
  const [response, setResponse] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  // Load first endpoint by default
  useEffect(() => {
    handleSelectEndpoint(ENDPOINTS[0].items[0]);
  }, []);

  const handleSelectEndpoint = (endpoint) => {
    setSelectedEndpoint(endpoint);
    setPathParams({});
    setBody(endpoint.body ? JSON.stringify(endpoint.body, null, 2) : '');
    setResponse(null);
    setError(null);
  };

  const handleParamChange = (key, value) => {
    setPathParams(prev => ({ ...prev, [key]: value }));
  };

  const buildUrl = () => {
    if (!selectedEndpoint) return '';
    let url = selectedEndpoint.url;
    if (selectedEndpoint.params) {
      selectedEndpoint.params.forEach(param => {
        const val = pathParams[param] || `{${param}}`;
        url = url.replace(`{${param}}`, val);
      });
    }
    return baseUrl + url;
  };

  const handleSendRequest = async () => {
    if (!selectedEndpoint) return;
    setLoading(true);
    setResponse(null);
    setError(null);

    try {
      const config = {
        method: selectedEndpoint.method,
        url: buildUrl(),
        data: body ? JSON.parse(body) : undefined,
        headers: { 'Content-Type': 'application/json' }
      };

      const res = await axios(config);
      setResponse({
        status: res.status,
        data: res.data,
      });
    } catch (err) {
      setError({
        message: err.message,
        response: err.response ? {
          status: err.response.status,
          data: err.response.data
        } : null
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-container">
      {/* Sidebar: Endpoint List */}
      <div className="sidebar">
        <h2>API Explorer</h2>
        {ENDPOINTS.map((cat, i) => (
          <div key={i} className="category-group">
            <h3>{cat.category}</h3>
            <ul>
              {cat.items.map((ep, j) => (
                <li
                  key={j}
                  className={selectedEndpoint === ep ? 'active' : ''}
                  onClick={() => handleSelectEndpoint(ep)}
                >
                  <span className={`method-badge ${ep.method}`}>{ep.method}</span>
                  <span className="ep-name">{ep.name}</span>
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>

      {/* Main Content: Request Details */}
      <div className="main-content">
        {selectedEndpoint ? (
          <div className="request-panel">
            <div className="header">
              <h1>{selectedEndpoint.name}</h1>
              <p className="description">{selectedEndpoint.desc}</p>
            </div>

            <div className="url-bar">
              <span className={`method-badge large ${selectedEndpoint.method}`}>{selectedEndpoint.method}</span>
              <input type="text" readOnly value={buildUrl()} className="url-input" />
              <button disabled={loading} onClick={handleSendRequest}>
                {loading ? 'Sending...' : 'Send Request'}
              </button>
            </div>

            <div className="configuration">
              {/* Path Parameters Section */}
              {selectedEndpoint.params && (
                <div className="section">
                  <h3>Path Parameters</h3>
                  <div className="grid-form">
                    {selectedEndpoint.params.map(param => (
                      <div key={param} className="form-group">
                        <label>{param}</label>
                        <input
                          type="text"
                          placeholder={`Value for ${param}`}
                          value={pathParams[param] || ''}
                          onChange={(e) => handleParamChange(param, e.target.value)}
                        />
                      </div>
                    ))}
                  </div>
                </div>
              )}

              {/* Request Body Section */}
              {(selectedEndpoint.method === 'POST' || selectedEndpoint.method === 'PUT') && (
                <div className="section">
                  <h3>Request Body (JSON)</h3>
                  <textarea
                    value={body}
                    onChange={(e) => setBody(e.target.value)}
                    rows={8}
                    className="code-editor"
                  />
                </div>
              )}
            </div>

            {/* Response Section */}
            <div className="response-section">
              <h3>Response</h3>
              {error && (
                <div className="response-box error">
                  <div className="status-line">Error: {error.message}</div>
                  {error.response && <pre>{JSON.stringify(error.response, null, 2)}</pre>}
                </div>
              )}
              {response && (
                <div className="response-box success">
                  <div className="status-line">Status: {response.status}</div>
                  <pre>{JSON.stringify(response.data, null, 2)}</pre>
                </div>
              )}
              {!response && !error && <div className="response-box empty">No response yet</div>}
            </div>

          </div>
        ) : (
          <div className="empty-state">Select an endpoint to get started</div>
        )}
      </div>
    </div>
  );
}

export default App;
