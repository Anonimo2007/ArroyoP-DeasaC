# Retrospectiva — slotMachine, Ciclo 1.

**Autores:** Gian Franco Arroyo Pérez, Nicolas Deaza Casasbuenas
**Curso:** DOPO-POOB — Escuela Colombiana de Ingeniería.

## 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

Definimos 5 mini-ciclos incrementales, cada uno entregando una máquina
funcional, siguiendo el principio de extensibilidad pedido en el enunciado:

1. **Mini-ciclo 1 — Esqueleto y catálogo de símbolos**: 'SlotMachine',
   'Symbol', 'CssColors'. Objetivo: poder crear la máquina y
   administrar el catálogo ('addSymbol', 'delSymbol', 'symbols',
   'distinctSymbols') sin ruedas ni interfaz gráfica todavía.
2. **Mini-ciclo 2 — Ruedas sin interfaz gráfica**: 'Wheel' (solo lógica),
   'addWheel', 'delWheel', 'placeSymbol'. Priorizamos la lógica antes que
   el dibujo para poder probar con JUnit/pruebas manuales rápido.
3. **Mini-ciclo 3 — Girar y consultar**: 'spin(wheel)', 'spin()',
   'configuration()', 'isJackpot()'.
4. **Mini-ciclo 4 — Representación visual**: integración con 'shapes'
   ('Rectangle', 'Circle', `Canvas`), 'makeVisible'/'makeInvisible',
   extensión de 'Canvas' para colores CSS completos, resaltado visual del
   jackpot.
5. **Mini-ciclo 5 — Usabilidad y cierre**: mensajes con 'JOptionPane'
   (solo si 'visible == true'), manejo de 'ok()', 'exit()', clamping de
   posiciones, documentación Javadoc y limpieza final.

Elegimos este orden porque separa **lógica de dominio** (mini-ciclos 1-3,
fáciles de probar sin interfaz gráfica) de **presentación** (mini-ciclo
4), y dejamos usabilidad/errores al final porque dependía de tener toda
la lógica ya estable.

## 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los 5 mini-ciclos están completos para este ciclo 1: la
máquina permite crear/eliminar ruedas y símbolos, colocar símbolos en
ruedas, girar individualmente o todas a la vez, consultar símbolos,
distintos y configuración, detectar jackpot, alternar visibilidad
(funcionando también invisible), y terminar el simulador. Quedó pendiente
para un futuro ciclo resolver el problema de la maratón en sí (no se pedía
en esta entrega) y enriquecer la interfaz visual (por ejemplo animar el
giro en vez de mostrar el resultado final directamente).

## 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)


| Integrante | Diseño | Código | Pruebas | Documentación | Total |
|---|---|---|---|---|---|
| Gian Franco Arroyo Pérez | 1 h | 3 h | 1 h | 0.5 h | 5.5 h |
| Nicolas Deaza Casasbuenas | 1.5 h | 3 h | 1 h | 0 h | 5.5 h |

## 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

Un logro importante fue lograr que la máquina funcionara
correctamente tanto visible como invisible sin duplicar lógica: todas las
operaciones de negocio (agregar/quitar ruedas y símbolos, girar, consultar
configuración) son independientes de si el 'Canvas' está mostrándose,
porque las clases 'Circle'/'Rectangle' de 'shapes' ya ignoran el dibujo
cuando 'isVisible == false'. Eso nos permitió cumplir el requisito de
extensibilidad sin condicionales repetidos por todo el código.

## 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

El mayor reto fue que el 'Canvas' original del proyecto
'shapes' solo reconocía seis nombres de color fijos, y el enunciado exige
que los símbolos usen el estándar CSS completo. Como el color se resuelve
en un método privado ('setForegroundColor'), no podíamos "parchearlo"
desde afuera sin romper el encapsulamiento. Decidimos extender el
componente creando una clase nueva 'CssColors' con la tabla de colores
CSS3 y modificar únicamente esa línea de 'Canvas' para delegar en ella,
en vez de reescribir todo el paquete 'shapes'.

## 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Hicimos bien dividir el trabajo por mini-ciclos verticales
(cada uno entregaba una funcionalidad completa y probada) en vez de
dividir por capas, lo que evitó bloqueos entre nosotros. Nos
comprometemos a escribir pruebas más temprano en el ciclo (antes de la
parte visual) y a hacer confirmaciones más pequeñas y frecuentes para facilitar
la revisión mutua del código.

## 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

AJUSTAR El diseño simple ("simple design") fue la práctica más útil:
mantener cada método dentro de una sola pantalla y con una sola
responsabilidad (como exige el enunciado) obligó a extraer clases de
apoyo ('Wheel', 'Symbol') en lugar de acumular lógica en 'SlotMachine',
lo que hizo el código más fácil de razonar y de extender en mini-ciclos
posteriores.

## 8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.

- Barnes, D. J., & Kölling, M. (2002). *Objects First with Java: A
  Practical Introduction Using BlueJ*. Pearson Education. (Base del
  proyecto `shapes` reutilizado).
- World Wide Web Consortium. (2022). *CSS Color Module Level 3*.
  https://www.w3.org/TR/css-color-3/
- Oracle. (2024). *How to Write Doc Comments for the Javadoc Tool*.
  https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html

La referencia más útil fue la especificación *CSS Color Module Level 3*
del W3C, porque de ahí tomamos los nombres y valores RGB exactos de los
colores estándar que debía reconocer `CssColors`.
