# Use a lighter version of Node as a parent image
FROM node:14.5.0-alpine3.12
# Set the working directory to /frontend
WORKDIR /frontend
# copy package.json into the container at /frontend
COPY package*.json /frontend/
# install dependencies
RUN npm install react-scripts -g --silent && npm install --silent
# Copy the current directory contents into the container at /frontend
COPY . /frontend/
# Run the app when the container launches
CMD ["npm", "start"]