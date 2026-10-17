# Compilare Fiber Circuit Viewer in APK (senza Android Studio)

Questa cartella contiene **il codice sorgente**, non un APK già compilato.
La procedura usa GitHub Actions per creare un APK Android di prova.

## Preparazione (solo la prima volta)

1. Apri https://github.com e accedi con un account GitHub (creane uno, se necessario).
2. Seleziona **New repository** e chiamalo `FiberCircuitViewer`.
3. Scegli **Private**: i file del codice restano visibili solo a chi autorizzi. Non caricare circuiti Fastweb reali o documenti aziendali.
4. Crea il repository **senza** aggiungere README, .gitignore o licenza: servirà per caricare i nostri file tutti insieme.
5. Decomprimi lo ZIP che hai ricevuto. Apri la cartella estratta `FiberCircuitViewer` (o la cartella root, se lo ZIP non contiene una cartella principale).
6. Nella pagina del repository appena creato, segui il collegamento **uploading an existing file** (oppure **Add file > Upload files**).
7. Trascina **il contenuto della cartella** del progetto: `app`, `.github`, `build.gradle`, `settings.gradle`, `gradle.properties`, `README.md` e `ISTRUZIONI_APK.md`.
   **Attenzione:** `app` e `.github` devono essere **nella radice** del repository, non dentro un'altra cartella `FiberCircuitViewer`.
8. Premi **Commit changes** per confermare il caricamento.

## Compilazione

1. Vai nella scheda **Actions** del repository.
2. Se GitHub ti chiede di autorizzare i workflow, approva.
3. Apri il workflow **Compila APK Android**.
4. Premi **Run workflow** → **Run workflow** (se parte già in automatico dopo il caricamento, puoi usare quell'esecuzione).
5. Attendi che l'esecuzione diventi verde. Il primo build deve scaricare dipendenze e può impiegare alcuni minuti.
6. Clicca sull'esecuzione e trova la sezione **Artifacts**.
7. Scarica **FiberCircuitViewer-APK**: è uno ZIP prodotto da GitHub che contiene `app-debug.apk`.
8. Estrai `app-debug.apk` e trasferiscilo sul Samsung A36 (o HONOR Magic V2).
9. Tocca il file APK sul telefono e segui i passaggi di Android per autorizzare l'installazione da quella specifica sorgente, poi installalo. Disattiva nuovamente l'autorizzazione dopo l'installazione se non ti serve.

L'APK è una **versione debug firmata automaticamente**, installabile per uso personale. Non serve Google Play né account sviluppatore Android.

## Limiti

- La compilazione non è stata eseguita su questa macchina, quindi potrebbe richiedere correzioni in caso di errori sulla piattaforma di build online.
- Installazioni successive compilate con chiavi debug diverse potrebbero non aggiornare l'app esistente: in quel caso disinstallala e installa la nuova versione. Il visualizzatore non memorizza circuiti permanentemente, ma conserva una copia dei file originali prima di disinstallare.
- I file HTML aziendali non fanno parte del progetto e non devono essere caricati nel repository.
- L'app non richiede il permesso Internet; consulta i circuiti localmente. Un collegamento Maps viene aperto in un'app esterna solo se toccato.
