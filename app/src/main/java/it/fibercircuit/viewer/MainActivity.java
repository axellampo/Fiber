package it.fibercircuit.viewer;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.JavascriptInterface;
import android.view.View;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private static final int PICK_FILE = 1201;
    private WebView web;
    private boolean ready = false;
    private Uri waitingUri;
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        web = new WebView(this);
        web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setAllowFileAccess(false);
        web.getSettings().setAllowContentAccess(true);
        web.addJavascriptInterface(new Bridge(), "AndroidBridge");
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient(){
            @Override public void onPageFinished(WebView view,String url) {
                ready = true;
                if(waitingUri != null) { Uri uri = waitingUri; waitingUri = null; importUri(uri); }
            }
            @Override public boolean shouldOverrideUrlLoading(WebView view, android.webkit.WebResourceRequest request) {
                Uri uri = request.getUrl();
                if("https".equals(uri.getScheme()) || "geo".equals(uri.getScheme())) {
                    try { startActivity(new Intent(Intent.ACTION_VIEW,uri)); } catch(Exception ignored) {}
                }
                return true;
            }
        });
        setContentView(web);
        web.loadUrl("file:///android_asset/index.html");
        if(getIntent()!=null && Intent.ACTION_VIEW.equals(getIntent().getAction())) waitingUri=getIntent().getData();
    }
    @Override protected void onNewIntent(Intent intent) { super.onNewIntent(intent); setIntent(intent); if(Intent.ACTION_VIEW.equals(intent.getAction())) { if(ready)importUri(intent.getData());else waitingUri=intent.getData(); } }
    public class Bridge {
        @JavascriptInterface public void pickFile() { runOnUiThread(() -> {
            Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            i.putExtra(Intent.EXTRA_MIME_TYPES,new String[]{"text/html","text/plain","application/octet-stream"});
            startActivityForResult(i,PICK_FILE);
        }); }
        @JavascriptInterface public void copy(String text) { runOnUiThread(() -> {
            android.content.ClipboardManager clipboard=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(android.content.ClipData.newPlainText("ID circuito",text));
            android.widget.Toast.makeText(MainActivity.this,"Copiato",android.widget.Toast.LENGTH_SHORT).show();
        }); }
    }
    @Override protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_FILE && resultCode==RESULT_OK && data!=null) importUri(data.getData());
    }
    private void importUri(Uri uri) {
        if(uri==null)return;
        try(InputStream input=getContentResolver().openInputStream(uri);ByteArrayOutputStream output=new ByteArrayOutputStream()) {
            byte[] buffer=new byte[8192];int n; int count=0;
            while((n=input.read(buffer))!=-1) {count+=n; if(count>5_000_000)throw new Exception("File superiore a 5 MB");output.write(buffer,0,n);}
            String html=output.toString(StandardCharsets.UTF_8.name());
            if(html.indexOf('\uFFFD')>=0) html=new String(output.toByteArray(),java.nio.charset.Charset.forName("windows-1252"));
            String name="Circuito HTML";
            try(android.database.Cursor cursor=getContentResolver().query(uri,null,null,null,null)) {
                if(cursor!=null && cursor.moveToFirst()) { int c=cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);if(c>=0)name=cursor.getString(c); }
            } catch(Exception ignored){}
            String js="window.receiveHtml("+JSONObject.quote(html)+","+JSONObject.quote(name)+")";
            web.evaluateJavascript(js,null);
        }catch(Exception e){ android.widget.Toast.makeText(this,"Impossibile aprire: "+e.getMessage(),android.widget.Toast.LENGTH_LONG).show(); }
    }
    @Override public void onBackPressed(){ web.evaluateJavascript("window.resetSearchAndScroll && window.resetSearchAndScroll()",null); }
}
