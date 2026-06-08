# Changelog

All notable changes to this project are documented here, organized by
Hyperskill stage.

## Stage 6 — Red, yellow, green

Each road in the System view now displays its open/closed state and a
countdown until its state flips. The road at the front of an internal
rotation sequence is open; on expiry it moves to the rotation's rear and the
next road opens with a fresh interval. Display order remains fixed insertion
order (FIFO add/delete) and never reorders; closed roads' remaining time is
derived from the open road's timer plus their offset in the rotation. Adding
or deleting a road — including deleting the currently-open one — recalculates
timings immediately and consistently.

## Stage 5 — Over and over again

Replaced the stub Add/Delete handlers with a real fixed-capacity circular
queue (capacity = number of roads). Options 1 and 2 add/remove roads in FIFO
order with "queue is full"/"queue is empty" guards, and the System view now
lists every road currently in the queue, front to rear.

## Stage 4 — Like a clockwork

Introduced the background `QueueThread` (named `"QueueThread"`), which
increments an elapsed-time counter every second independent of the menu and
prints live system information (elapsed time, configured settings) whenever
the user is in System mode. Selecting option 3 switches into System mode;
pressing Enter returns to the menu.

## Stage 3 — Oops, wrong button

Added input validation: the road count and interval prompts now loop until a
positive integer is entered, and the menu rejects any option outside `0`-`3`
with an error message. The console is cleared after each action and the user
must press Enter to continue.

## Stage 2 — Set up the traffic light

Added the setup prompts for number of roads and interval, wrapped the menu in
a loop, and wired up stub handlers for each menu option (Add road, Delete
road, Open system, Quit).

## Stage 1 — Open the control panel

Initial implementation: prints the six-line welcome message and menu with no
further interaction.
