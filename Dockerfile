FROM nginx:stable-alpine

ENV VITE_API_URL=https://www.cokkirimarket.link

COPY ./dist /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]