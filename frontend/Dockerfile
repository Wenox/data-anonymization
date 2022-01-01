# Step 1: build
FROM node:17-alpine as build
WORKDIR /usr/src/anonymisation_client
COPY package.json ./
COPY yarn.lock ./
RUN yarn install --frozen-lockfile
COPY . ./
RUN yarn run build

# Step 2: production
FROM nginx:stable-alpine
COPY --from=build /usr/src/anonymisation_client/build /usr/share/nginx/html
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]
