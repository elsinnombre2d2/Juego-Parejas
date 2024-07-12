# Usa una imagen base de Node.js
FROM node:alpine

# Establece el directorio de trabajo en el contenedor
WORKDIR /usr/src/app

# Copia el archivo package.json y package-lock.json (si está disponible)
COPY . /usr/src/app/

# Instala las dependencias

RUN npm instal -g angular
RUN npm install


# Expone el puerto en el que tu aplicación estará corriendo
EXPOSE 8000

# Comando para iniciar la aplicación
CMD ["npm", "start"]
