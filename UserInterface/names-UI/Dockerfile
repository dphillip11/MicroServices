# Use an official nginx image as a parent image
FROM nginx:alpine

# Copy the built app to the nginx web root directory
COPY /dist/names-ui /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]

# Use a simple shell command to replace the placeholder with the environment variable value
# RUN sed -i "s|${BACKEND_URL}|${BACKEND_URL}|g" /usr/share/nginx/html/main*.js

