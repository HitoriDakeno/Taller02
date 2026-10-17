# Taller 2 - POO

## Descripción del proyecto

Este proyecto corresponde al Taller 2 POO.

El programa implementa un sistema de registro y administración de consumos para una fonda llamada **La Fonda del Papu**. Permite registrar los consumos de choripanes y terremotos realizados por los participantes, consultar los registros, modificar o eliminar consumos y calcular diferentes estadísticas.

El sistema permite conocer la cantidad total de choripanes y terremotos consumidos, los consumos de cada participante, las kilocalorías calculadas y los días en que una persona acumuló dos litros o más de terremoto en una misma fecha.

Los datos se almacenan en archivos de texto, lo que permite cargar los registros al iniciar el programa y guardar los cambios realizados.

## Integrante

- **David Villalobos** - 21.646.173-8 - ICCI - GitHub: [HitoriDakeno](https://github.com/HitoriDakeno)

## Estructura del proyecto

```text
Taller02/
├── src/
│   └── taller02/
│       ├── Main.java
│       ├── SistemaFonda.java
│       └── Consumo.java
├── Participantes.txt
├── Consumido.txt
├── README.md
└── DiagramaTaller02.pdf
```


## Paquete principal

`taller02`

## Clases principales

### Main.java

Es la clase principal del programa. Contiene el método `main`, desde el cual se crea el sistema, se cargan los archivos y se inicia el menú principal.

### SistemaFonda.java

Es la clase encargada de administrar el funcionamiento del sistema. Permite:

- Cargar los participantes y los consumos desde archivos de texto.
- Mostrar los registros de choripanes y terremotos.
- Agregar nuevos consumos.
- Editar y eliminar registros existentes.
- Calcular estadísticas y kilocalorías.
- Guardar los cambios realizados en los archivos.
- Validar los datos ingresados para evitar errores durante la ejecución.

### Consumo.java

Representa un consumo realizado por un participante. Almacena el tipo de producto, el nombre del consumidor, sus medidas o cantidad de litros y la fecha.

También permite calcular las kilocalorías de cada registro y generar el formato utilizado para guardar los datos en el archivo.

## Requisitos

- Java JDK instalado.
- Eclipse IDE o un entorno compatible con proyectos Java.
- Git instalado para clonar el repositorio.
- Los archivos `Participantes.txt` y `Consumido.txt` disponibles en la ubicación desde la cual el programa los busca.

## Instrucciones de ejecución

### 1. Clonar el repositorio

Abrir una terminal y ejecutar el siguiente comando, reemplazando la dirección por la URL real del repositorio:

```bash
git clone https://github.com/HitoriDakeno/Taller02.git
```

Luego, ingresar a la carpeta del proyecto.

### 2. Importar el proyecto en Eclipse

1. Abrir Eclipse.
2. Seleccionar **File > Import**.
3. Elegir **General > Existing Projects into Workspace**.
4. Seleccionar la carpeta del proyecto clonado.
5. Finalizar la importación.

### 3. Verificar los archivos de entrada

El programa utiliza los siguientes archivos:

- `Participantes.txt`: contiene los nombres de los participantes, uno por línea.
- `Consumido.txt`: contiene los registros de consumo guardados en el formato correspondiente.

Se deben mantener estos archivos en la ubicación desde la cual el programa los busca. Si no se encuentran, el sistema mostrará un mensaje de error y podrá iniciar sin los datos correspondientes.

### 4. Ejecutar el programa

1. Abrir `src/taller02/Main.java`.
2. Hacer clic derecho sobre el archivo.
3. Seleccionar **Run As > Java Application**.
4. Utilizar las opciones del menú que aparecen en la consola.

## Funcionamiento del programa

El menú principal cuenta con las siguientes opciones:

1. **Ver registros:** permite consultar los consumos de choripanes y terremotos.
2. **Actualizar registros:** permite agregar, editar y eliminar consumos.
3. **Calcular métricas:** permite obtener estadísticas sobre los consumos.
4. **Salir:** finaliza la ejecución del programa.

Dentro del menú de métricas se pueden consultar:

- Cantidad total de choripanes registrados.
- Cantidad total de terremotos y litros consumidos.
- Cantidad de choripanes por participante.
- Cantidad de terremotos y litros consumidos por participante.
- Kilocalorías consumidas por participante y total general.
- Días en que cada participante acumuló dos litros o más de terremoto en una misma fecha.

## Testeo

Para comprobar el funcionamiento del programa, se recomienda probar las siguientes opciones:

- Carga de participantes y consumos desde los archivos.
- Visualización de registros de ambos tipos de productos.
- Agregar un consumo de choripán.
- Agregar un consumo de terremoto.
- Editar los datos de un registro.
- Eliminar un registro y confirmar la eliminación.
- Cancelar una operación antes de completarla.
- Ingresar opciones fuera del rango permitido.
- Ingresar letras en campos que requieren números.
- Ingresar cantidades iguales o menores que cero.
- Ingresar fechas inválidas.
- Comprobar que los cambios realizados se guarden en `Consumido.txt`.
- Verificar que las estadísticas y las kilocalorías se calculen correctamente.

Estas pruebas permiten revisar el funcionamiento general del sistema y comprobar que las entradas incorrectas sean controladas.

## Diagrama de clases

El repositorio incluye el diagrama de clases del proyecto en formato PDF. En él se representan las clases principales, sus atributos, métodos y la relación entre ellas.

