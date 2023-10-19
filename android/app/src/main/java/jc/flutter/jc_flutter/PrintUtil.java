package jc.flutter.jc_flutter;

import android.app.Application;

import com.gengcon.www.jcprintersdk.JCPrintApi;
import com.gengcon.www.jcprintersdk.callback.Callback;

/**
 * 打印工具类
 *
 * @author zhangbin
 */
public class PrintUtil {
    private static final Callback CALLBACK = new Callback() {
        @Override
        public void onConnectSuccess(String s) {

        }

        @Override
        public void onDisConnect() {

        }

        @Override
        public void onElectricityChange(int i) {

        }

        @Override
        public void onCoverStatus(int i) {

        }

        @Override
        public void onPaperStatus(int i) {

        }

        @Override
        public void onRfidReadStatus(int i) {

        }

        @Override
        public void onPrinterIsFree(int i) {

        }

        @Override
        public void onHeartDisConnect() {

        }

        @Override
        public void onFirmErrors() {

        }
    };

    private static JCPrintApi api;

    public static void init(Application application) {
        api = JCPrintApi.getInstance(CALLBACK);
        api.init(application);
        api.initImageProcessingDefault("", "");
    }

    public static JCPrintApi getInstance() {
        return api;

    }

    public static int openPrinter(String address) {
        return api.openPrinterByAddress(address);
    }

    public static void close() {
        api.close();
    }

    public static int isConnection() {
        return api.isConnection();
    }
}
