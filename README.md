# IPM

## 2017/2018

## Práctica 2

#### Guillermo Martín Villar (g.mvillar@udc.es)

#### Juan Filgueiras Rilo (juan.filgueiras.rilo@udc.es)

### descripción:
	-se pide diseñar una aplicación para la plataforma android que actúe como cliente de una hoja de cálculo (Google Spreadsheet) que contiene información de cervezas
	-dicha aplicación debe ofrecer los siguientes casos de uso:
		-visualizar la lista de cervezas
		-visualizar la información de una cerveza
		-realizar un comentario sobre una cerveza (y reflejarlo en la hoja de cálculo)
	-para realizar esto se ha optado por una metodología incremental donde en cada incremento se implementa lo que se indica, más abajo, en los tasks
	-para los tasks 1, 2 y 3 se ha seguido el siguiente plan de trabajo:
		-diseño de la interfaz con mockups
		-diseño software de la aplicación
		-implementación del caso de uso
	-en el task final se ha procedido a arreglar aquellos fallos conocidos de la aplicación y a mejorar tanto su diseño software (evitar código redundante, etc.) como su diseño visual, y después se ha procedido a reflejar esto en los correspondientes mockups y UML

### task1: 
	-mostrar una lista con los nombres de las cervezas obtenidas de la hoja de cálculo

### task2: 
	-al seleccionar una cerveza de la lista, ver los detalles de dicha cerveza, incluida la foto
	-si se gira la pantalla, se muestra a un lado (izquierda) la lista de las cervezas, y a otro lado (derecha) los detalles de la cerveza seleccionada

### task3:
	-se permite realizar un nuevo comentario en una cerveza, esto es, se añade a los comentarios ya existentes otro nuevo comentario (se concatena)

### task4:
	-se crea una vista específica para tablets (anchos mayores de 900dp)
	-no hace falta adaptar la interface a distintos tamaños y resoluciones de pantalla porque ya está hecho desde el principio
	-no se actualiza el diseño de la interfaz ya que el comportamiento es el mismo que el de un smartphone en horizontal
	-no se actualiza el diseño software ya que no se toca nada del código

### task5:
	-se internacionaliza la aplicación para que se ajuste automáticamente a la configuración del entorno local (locale) del usuario.
	-concretamente se ha añadido soporte para los idiomas: castellano e inglés
	-no se actualiza el diseño de la interfaz ya que lo único que cambia es el formato de las fechas y el idioma de los textos
	-no se actualiza el diseño software ya que no se toca nada del código

### final:
	-después del task5 se procederá a rediseñar el diseño software de la aplicación y a rediseñar visualmente la aplicación para que sea más estética y cumpla con las guías de diseño de Android.
	-se sub