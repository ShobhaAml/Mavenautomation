# Shot creation manual test cases

Manual cases for creating a shot on the Emisión tab, one option at a time. Validation text is the copy the shot editor shows. Spec 2.6.2 is the shot form. Spec 2.6.6 is the Publicados, Programados, and Borradores groups.

The empty-shot message for a shot is `Nada que guardar`. `No se puede guardar un borrador vacío` is the RT form message, not the shot form message.

Shared setup, unless a case says otherwise:

1. Sign in on testadmin.
2. Open a superevento that is not closed. A start date and an end date are saved. The superevento does not have to be published.
3. Open the Emisión tab.
4. The SHOT tab is selected.

## Happy path

### TC_SHOT_001 Crear nuevo shot

Type: Happy

Option: Crear nuevo shot

Steps:

1. On Emisión, use the button whose tooltip is `Crear nuevo shot`.

Expected:

- The shot editor opens.
- The empty editor shows `Escribe aquí...`.

### TC_SHOT_002 Write shot text

Type: Happy

Option: Content editor

Steps:

1. Click the editor.
2. Type `Shot de prueba`.

Expected:

- `Escribe aquí...` is replaced by `Shot de prueba`.
- No validation message is shown.

### TC_SHOT_003 Bold

Type: Happy

Option: `[b]old`

Steps:

1. Type `texto bold`.
2. Apply the `[b]old` shortcut to that text.

Expected:

- The selected text is shown as bold.
- No validation message is shown.

### TC_SHOT_004 Italic

Type: Happy

Option: `[i]talic`

Steps:

1. Type `texto italic`.
2. Apply the `[i]talic` shortcut to that text.

Expected:

- The selected text is shown in italic.
- No validation message is shown.

### TC_SHOT_005 Heading

Type: Happy

Option: `h[2]`

Steps:

1. Type a line of text.
2. Apply the `h[2]` shortcut to that line.

Expected:

- The line is shown as a heading.
- No validation message is shown.

### TC_SHOT_006 Link

Type: Happy

Option: `[l]ink`

Steps:

1. Type `enlace`.
2. Apply the `[l]ink` shortcut and complete the link with a valid URL.

Expected:

- `enlace` becomes a link.
- No validation message is shown.

### TC_SHOT_007 Image

Type: Happy

Option: `imag[e]`

Steps:

1. Apply the `imag[e]` shortcut.
2. Add an image and confirm it.

Expected:

- The image is inserted in the shot.
- No validation message is shown.

### TC_SHOT_008 Publish now

Type: Happy

Option: `PUBLICAR YA`

Steps:

1. Type `Shot publicado`.
2. Click `PUBLICAR YA`.

Expected:

- The success text is `Publicado correctamente. Se verá en la web en unos segundos` when the site preview URL exists.
- If that preview URL is missing, the success text is `Publicado`.
- The shot is listed under `Publicados` and the count increases by one.

### TC_SHOT_009 Open schedule

Type: Happy

Option: Programar

Steps:

1. Type `Shot a programar`.
2. Click the button whose tooltip is `Programar`.

Expected:

- The schedule panel opens.
- The time field label is `Hora`.
- The confirm button is `PROGRAMAR`.
- No validation message is shown yet.

### TC_SHOT_010 Schedule with a valid time

Type: Happy

Option: `PROGRAMAR`

Steps:

1. Type `Shot programado`.
2. Open Programar.
3. Set `Hora` to a valid future time.
4. Click `PROGRAMAR`.

Expected:

- The success text is `Programado correctamente. Ver vista previa` when the site preview URL exists.
- If that preview URL is missing, the success text is `Programado`.
- The shot is listed under `Programados` and the count increases by one.

### TC_SHOT_011 Choose a slot

Type: Happy

Option: `ELEGIR HUECO`

Steps:

1. Type `Shot con hueco`.
2. Click `ELEGIR HUECO`.

Expected:

- The slot table opens.
- The same control now reads `CERRAR`.
- No validation message is shown.

### TC_SHOT_012 Close the slot table

Type: Happy

Option: `CERRAR`

Steps:

1. Open the slot table with `ELEGIR HUECO`.
2. Click `CERRAR`.

Expected:

- The slot table closes.
- The control reads `ELEGIR HUECO` again.
- The shot is not published and not scheduled.

### TC_SHOT_013 Refresh slots

Type: Happy

Option: Slot refresh

Steps:

1. Open the slot table.
2. Use the refresh control.

Expected:

- The slot table reloads.
- No validation message is shown.

### TC_SHOT_014 Slots today

Type: Happy

Option: Slot today

Steps:

1. Open the slot table.
2. Move to another day.
3. Use the today control.

Expected:

- The table returns to the current day.
- No validation message is shown.

### TC_SHOT_015 Go to a slot date

Type: Happy

Option: Slot go to date

Steps:

1. Open the slot table.
2. Use go to date and pick a date.

Expected:

- The table shows the chosen date.
- No validation message is shown.

### TC_SHOT_016 Previous slots

Type: Happy

Option: Slot previous

Steps:

1. Open the slot table.
2. Use the previous control.

Expected:

- The table moves to the previous range.
- No validation message is shown.

### TC_SHOT_017 Next slots

Type: Happy

Option: Slot next

Steps:

1. Open the slot table.
2. Use the next control.

Expected:

- The table moves to the next range.
- No validation message is shown.

### TC_SHOT_018 Save as draft

Type: Happy

Option: `Guardar como borrador`

Steps:

1. On a new shot, type `Shot borrador`.
2. Click the button whose tooltip is `Guardar como borrador`.

Expected:

- No validation error is shown.
- The shot is listed under `Borradores` and the count increases by one.

### TC_SHOT_019 Preview from the list

Type: Happy

Option: `Vista previa` on a saved shot in the list

Steps:

1. Publish or schedule a shot that has text.
2. On that row, click the button whose tooltip is `Vista previa`.

Expected:

- A preview of that shot opens.
- The composer message `Vista previa no disponible todavía` is not used for this list action.

### TC_SHOT_020 Delete a shot

Type: Happy

Option: `Eliminar`

Steps:

1. Open a shot that already has content.
2. Click the button whose tooltip is `Eliminar`.
3. In the dialog, click `ELIMINAR`.

Expected:

- The dialog title is `¿Seguro que quieres borrar el contenido del shot? No se podrá deshacer después`.
- The submit label is `ELIMINAR`.
- After confirm, the message is `Shot eliminado`.
- The shot is removed from its group and the count decreases by one.

### TC_SHOT_021 Move a shot to draft

Type: Happy

Option: `Pasar a borrador`

Steps:

1. Open a published or scheduled shot that has content.
2. Click the button whose tooltip is `Pasar a borrador`.
3. Confirm the dialog.

Expected:

- The dialog title is `¿Seguro que quieres pasar a borrador este contenido?`.
- The submit label is `PASAR A BORRADOR`.
- After confirm, the message is `Pasado a borrador`.
- The shot leaves `Publicados` or `Programados` and appears under `Borradores`.

### TC_SHOT_022 Publish changes

Type: Happy

Option: `PUBLICAR CAMBIOS`

Steps:

1. Open a shot that is already published.
2. Change the text.
3. Click `PUBLICAR CAMBIOS`.

Expected:

- The success text is `Actualizado correctamente. Se verá en la web en unos segundos` when the site preview URL exists.
- The shot stays under `Publicados`.

### TC_SHOT_023 Republish as most recent

Type: Happy

Option: `Republicar como el más reciente`

Steps:

1. Open schedule on a shot that shows this checkbox.
2. Check `Republicar como el más reciente`.
3. Set a valid time and click `PROGRAMAR`.

Expected:

- The shot is scheduled.
- The success text is `Programado correctamente. Ver vista previa` when the site preview URL exists.

### TC_SHOT_024 Sticky toolbar

Type: Happy

Option: Sticky toolbar

Steps:

1. Open a shot with enough content to scroll.
2. Scroll until the footer toolbar leaves the view.

Expected:

- The toolbar with `data-testid` `micropost-sticky-toolbar` stays on screen.
- `PUBLICAR YA` and `Programar` remain usable.

### TC_SHOT_025 Published group

Type: Happy

Option: `Publicados`

Steps:

1. Publish one shot.
2. Read the `Publicados` header.

Expected:

- The header text starts with `Publicados` and includes the count in parentheses.
- The new shot is inside that group.

### TC_SHOT_026 Scheduled group

Type: Happy

Option: `Programados`

Steps:

1. Schedule one shot.
2. Read the `Programados` header.

Expected:

- The header text starts with `Programados` and includes the count in parentheses.
- The new shot is inside that group.

### TC_SHOT_027 Draft group

Type: Happy

Option: `Borradores`

Steps:

1. Save one shot as a draft.
2. Read the `Borradores` header.

Expected:

- The header text starts with `Borradores` and includes the count in parentheses.
- The new shot is inside that group.

### TC_SHOT_028 Fold a group

Type: Happy

Option: `Plegar`

Steps:

1. On an open group, click the header whose label is `Plegar`.

Expected:

- The group body is hidden.
- The control label changes to `Desplegar`.

### TC_SHOT_029 Unfold a group

Type: Happy

Option: `Desplegar`

Steps:

1. Fold a group.
2. Click the header whose label is `Desplegar`.

Expected:

- The shots in that group are shown again.
- The control label changes to `Plegar`.

### TC_SHOT_030 Go to day

Type: Happy

Option: `IR AL DÍA...`

Steps:

1. On a day that shows `IR AL DÍA...`, click it.

Expected:

- The view moves to that day.
- No validation message is shown.

### TC_SHOT_031 Expand all

Type: Happy

Option: `Ver todo desplegado`

Steps:

1. Open the list menu.
2. Choose `Ver todo desplegado`.

Expected:

- Every shot group is expanded.
- The menu item is selected.
- No validation message is shown.

### TC_SHOT_032 Only my content

Type: Happy

Option: `Ver sólo tus contenidos`

Steps:

1. Open the list menu.
2. Choose `Ver sólo tus contenidos`.

Expected:

- The list shows only shots owned by the signed-in user.
- The menu item is selected.
- No validation message is shown.

### TC_SHOT_033 Paste a copied section

Type: Happy

Option: Paste

Steps:

1. Copy a content section.
2. Paste it into the shot editor.

Expected:

- The copied section is added.
- The tooltip is not `Nada que pegar`.

### TC_SHOT_034 Create another shot

Type: Happy

Option: `Crear nuevo shot` after one shot exists

Steps:

1. Publish or save a shot.
2. Click `Crear nuevo shot` again.

Expected:

- A new empty editor opens.
- The placeholder is `Escribe aquí...`.
- The previous shot remains in its group.

## Negative

### TC_SHOT_035 Publish with an empty editor

Type: Negative

Option: `PUBLICAR YA`

Steps:

1. Leave the editor empty.
2. Click `PUBLICAR YA`.

Expected:

- The message is `Nada que guardar`.
- No shot is added to `Publicados`.

### TC_SHOT_036 Publish with only spaces

Type: Negative

Option: `PUBLICAR YA`

Steps:

1. Type only spaces in the editor.
2. Click `PUBLICAR YA`.

Expected:

- The message is `Nada que guardar`.
- No shot is added to `Publicados`.

### TC_SHOT_037 Schedule with an empty editor

Type: Negative

Option: `PROGRAMAR`

Steps:

1. Leave the editor empty.
2. Open Programar, set a valid time, and click `PROGRAMAR`.

Expected:

- The message is `Nada que guardar`.
- No shot is added to `Programados`.

### TC_SHOT_038 Schedule with an invalid time

Type: Negative

Option: `Hora`

Steps:

1. Type `Shot con hora mala`.
2. Open Programar.
3. Clear or break the `Hora` value so it is not a time.
4. Click `PROGRAMAR`.

Expected:

- The message is `Fecha u hora no válida`.
- No shot is added to `Programados`.

### TC_SHOT_039 Save an empty draft

Type: Negative

Option: `Guardar como borrador`

Steps:

1. Leave the editor empty.
2. Click `Guardar como borrador`.

Expected:

- The message is `Nada que guardar`.
- No shot is added to `Borradores`.

### TC_SHOT_040 Move an empty shot to draft

Type: Negative

Option: `Pasar a borrador`

Steps:

1. Clear all content from a shot.
2. Click `Pasar a borrador` and confirm if the dialog opens.

Expected:

- The message is `Nada que guardar`.
- The shot does not move to `Borradores` because of this empty save.

### TC_SHOT_041 Cancel delete

Type: Negative

Option: `Eliminar`

Steps:

1. Open a shot that has content.
2. Click `Eliminar`.
3. Cancel the dialog.

Expected:

- The dialog title is `¿Seguro que quieres borrar el contenido del shot? No se podrá deshacer después`.
- The shot is still present.
- `Shot eliminado` is not shown.

### TC_SHOT_042 Cancel move to draft

Type: Negative

Option: `Pasar a borrador`

Steps:

1. Open a published shot that has content.
2. Click `Pasar a borrador`.
3. Cancel the dialog.

Expected:

- The dialog title is `¿Seguro que quieres pasar a borrador este contenido?`.
- The shot stays in its original group.
- `Pasado a borrador` is not shown.

### TC_SHOT_043 Follow-up already closed

Type: Negative

Option: `Crear nuevo shot`

Steps:

1. Open Emisión on a superevento whose follow-up is closed.

Expected:

- The message is `Seguimiento terminado. Para volver a emitir, hay que reanudarlo en "Publicación"`.
- The shot editor is not available.

### TC_SHOT_044 Paste with nothing copied

Type: Negative

Option: Paste

Steps:

1. Make sure no section is copied.
2. Look at the paste control in the shot editor.

Expected:

- The paste control is disabled.
- Its tooltip is `Nada que pegar`.

### TC_SHOT_045 Preview from the composer

Type: Negative

Option: `Vista previa` in the shot composer

Steps:

1. Type `Shot sin vista`.
2. Click the composer button whose tooltip is `Vista previa`.

Expected:

- The message is `Vista previa no disponible todavía`.
- The shot is not published.

### TC_SHOT_046 Publish rejected by the server

Type: Negative

Option: `PUBLICAR YA`

Steps:

1. Type `Shot publicar fallo`.
2. Click `PUBLICAR YA` while the publish request fails.

Expected:

- The message is `Error al publicar el shot`.
- The shot is not shown as published.

### TC_SHOT_047 Schedule rejected by the server

Type: Negative

Option: `PROGRAMAR`

Steps:

1. Type `Shot programar fallo`.
2. Set a valid time.
3. Click `PROGRAMAR` while the schedule request fails.

Expected:

- The message is `Error al programar el shot`.
- The shot is not shown as scheduled.

### TC_SHOT_048 Delete rejected by the server

Type: Negative

Option: `Eliminar`

Steps:

1. Confirm `ELIMINAR` while the delete request fails.

Expected:

- The message is `Error al eliminar el shot`.
- The shot remains in its group.

### TC_SHOT_049 Save draft rejected by the server

Type: Negative

Option: `Guardar como borrador`

Steps:

1. Type `Shot borrador fallo`.
2. Click `Guardar como borrador` while the save request fails.

Expected:

- The message is `Error al guardar el shot`.
- The shot is not stored as a draft.

### TC_SHOT_050 Move to draft rejected by the server

Type: Negative

Option: `Pasar a borrador`

Steps:

1. Confirm `PASAR A BORRADOR` on a published shot while the request fails.

Expected:

- The message is `Error al pasar a borrador`.
- The shot stays out of `Borradores`.

### TC_SHOT_051 Sync rejected by the server

Type: Negative

Option: Shot sync

Steps:

1. Save a shot with text while the backend sync request fails.

Expected:

- The message is `Error al sincronizar el shot con el backend`.

### TC_SHOT_052 Shot list fails to load

Type: Negative

Option: `Publicados`, `Programados`, and `Borradores`

Steps:

1. Open Emisión while the shot list request fails.

Expected:

- The message is `Error al cargar los shots`.
- The three groups are not shown as a successful list.
