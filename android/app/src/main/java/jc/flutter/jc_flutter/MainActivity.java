package jc.flutter.jc_flutter;

import io.flutter.embedding.android.FlutterActivity;

public class MainActivity extends FlutterActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrintUtil.init(getApplication());
    }


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        flutterEngine.getPlugins().add(new BluePlugin());
        super.configureFlutterEngine(flutterEngine);
    }
}
