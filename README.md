# ArroyoP-DeasaC
Simulador de máquina tragamonedas — DOPO

Escuela Colombiana de Ingenieria - DOPO
Proyecto Inicial, Ciclo No. 1, 2026-2
Autores: Gian Franco Arroyo Perez, Nicolas Deaza Casasbuenas

Simulador de una maquina tragamonedas inspirado en el problema "Slot
Machine" (ICPC 2025, Problem I).

Clases:
 - SlotMachine : clase principal (fachada), interfaz publica requerida.
 - Wheel       : una rueda/reel de la maquina (clase de apoyo).
 - Symbol      : un simbolo identificado por su color (clase de apoyo).
 - CssColors   : extension del proyecto shapes; traduce nombres de
                 color estandar CSS3 a java.awt.Color.
 - Circle, Rectangle, Triangle, Canvas : componentes reutilizados del
   proyecto shapes (Barnes & Kolling, "Objects First with Java").
   Canvas fue extendido unicamente en setForegroundColor para admitir
   el catalogo completo de colores CSS (antes solo aceptaba 6 nombres).

Para probar interactivamente en BlueJ: click derecho sobre SlotMachine
-> new SlotMachine() -> invocar los metodos desde el object bench.
