# Fiber Circuit Viewer

Visualizzatore Android offline per i due formati HTML Fastweb forniti: GPON testuale e report segmento tabellare.

## Creare APK con Android Studio
1. Aprire la cartella `FiberCircuitViewer` con Android Studio (File > Open).
2. Attendere il sync Gradle; è necessaria connessione Internet al primo sync per scaricare Android Gradle Plugin e SDK Platform 35, se non già disponibili.
3. Selezionare **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. APK debug generato in `app/build/outputs/apk/debug/app-debug.apk`.
5. Trasferire l'APK su Android e installarlo (autorizzando installazione da quella sorgente se richiesto).

## Prova immediata senza APK
Aprire `FiberCircuitViewer_anteprima.html` in un browser e caricare uno dei circuiti con il bottone "Apri HTML". Per test locale offline su Android, l'accesso ai file potrebbe dipendere dal browser: nell'app nativa si usa il selettore documenti Android.

## Privacy
I circuiti sono letti ed elaborati esclusivamente nella WebView locale. Nessun upload e nessun permesso Internet. I link Maps vengono aperti solo quando toccati.

## Limiti
Parser testato strutturalmente sui due esempi ricevuti; possono esistere altre varianti del gestionale da supportare. L'HTML originale resta immutato. `Mostra HTML originale` espone il sorgente testuale, non il rendering originale.
