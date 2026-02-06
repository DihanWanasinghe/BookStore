# Stage 1: Build the React application
FROM node:20-alpine AS builder

WORKDIR /app

COPY package.json yarn.lock ./
# Enable Corepack to use the version of Yarn specified in package.json
RUN corepack enable
# Using yarn to install dependencies
RUN yarn install --frozen-lockfile

COPY . .
RUN yarn build

# Stage 2: Serve the application with Nginx
FROM nginx:alpine

COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
