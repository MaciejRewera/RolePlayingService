# pull official base image
FROM node:14-alpine AS development
ENV NODE_ENV development

# Add a work directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# Cache and Install dependencies
COPY package.json .
COPY package-lock.json ./
RUN npm install
RUN npm install react-scripts@3.4.1 -g

# Copy app files
COPY . ./

# Expose port
EXPOSE 3000

# Start the app
CMD [ "npm", "start" ]
#RUN npm start