package jc.flutter.jc_flutter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gengcon.www.jcprintersdk.callback.PrintCallback;
import com.xunlv.jincai.xunlv_jincai_flutter_pro.utils.PrintUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class BluePlugin implements FlutterPlugin {
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        MethodChannel channel = new MethodChannel(binding.getBinaryMessenger(), "blue_plugin");
        channel.setMethodCallHandler(this::onMethodCall);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {

    }

    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "queryBlueDevice": {
                queryBlueDevice(result);
                break;
            }
            case "print": {
                print(call, result);
                break;
            }
            case "openPrinter": {
                openPrinter(call, result);
                break;
            }
            case "closePrinter": {
                closePrinter(result);
                break;
            }
            default: {
                result.success(null);
                break;
            }
        }
    }

    public void print(MethodCall call, MethodChannel.Result result) {

        Map<String, Object> parameter = call.arguments();
        Log.d("测试打印", "parameter class " + parameter.getClass());

        int printDensity = 3;
        int printMode = 1;
        int quantity = 1;
        Float printMultiple = 11.81F;

      /*  int printDensity = 8;
        int printMode = 2;
        int quantity = 1;
        Float printMultiple = 8F;*/


        //重置错误状态变量
        final boolean[] isError = {false};
        final String[] errorMsg = {""};

        PrintUtil.getInstance().setTotalQuantityOfPrints(1);
        PrintUtil.getInstance().startPrintJob(printDensity, 1, printMode, new PrintCallback() {
            @Override
            public void onProgress(int pageIndex, int quantityIndex, HashMap<String, Object> hashMap) {
                Log.d("测试打印", "测试:打印进度:已打印到第" + pageIndex + "页,第" + quantityIndex + "份");
                if (PrintUtil.getInstance().endJob()) {
                    Log.d("测试打印", "结束打印成功");
                } else {
                    Log.d("测试打印", "结束打印失败");
                }
            }

            @Override
            public void onError(int i) {
                Log.d("测试打印", "onError");
            }

            @Override
            public void onError(int errorCode, int printState) {
                Log.d("测试打印", "onError: " + errorCode);
                isError[0] = true;
                switch (errorCode) {
                    case 1:
                        errorMsg[0] = "盒盖打开";
                        break;
                    case 2:
                        errorMsg[0] = "缺纸";
                        break;
                    case 3:
                        errorMsg[0] = "电量不足";
                        break;
                    case 4:
                        errorMsg[0] = "电池异常";
                        break;
                    case 5:
                        errorMsg[0] = "手动停止";
                        break;
                    case 6:
                        errorMsg[0] = "数据错误";
                        break;
                    case 7:
                        errorMsg[0] = "温度过高";
                        break;
                    case 8:
                        errorMsg[0] = "出纸异常";
                        break;
                    case 9:
                        errorMsg[0] = "正在打印";
                        break;
                    case 10:
                        errorMsg[0] = "没有检测到打印头";
                        break;
                    case 11:
                        errorMsg[0] = "环境温度过低";
                        break;
                    case 12:
                        errorMsg[0] = "打印头未锁紧";
                        break;
                    case 13:
                        errorMsg[0] = "未检测到碳带";
                        break;
                    case 14:
                        errorMsg[0] = "不匹配的碳带";
                        break;
                    case 15:
                        errorMsg[0] = "用完的碳带";
                        break;
                    case 16:
                        errorMsg[0] = "不支持的纸张类型";
                        break;
                    case 17:
                        errorMsg[0] = "纸张类型设置失败";
                        break;
                    case 18:
                        errorMsg[0] = "打印模式设置失败";
                        break;
                    case 19:
                        errorMsg[0] = "设置浓度失败";
                        break;
                    case 20:
                        errorMsg[0] = "写入rfid失败";
                        break;
                    case 21:
                        errorMsg[0] = "边距设置失败";
                        break;
                    case 22:
                        errorMsg[0] = "通讯异常";
                        break;
                    case 23:
                        errorMsg[0] = "打印机连接断开";
                        break;
                    case 24:
                        errorMsg[0] = "画板参数错误";
                        break;
                    case 25:
                        errorMsg[0] = "旋转角度错误";
                        break;
                    case 26:
                        errorMsg[0] = "json参数错误";
                        break;
                    case 27:
                        errorMsg[0] = "出纸异常(B3S)";
                        break;
                    case 28:
                        errorMsg[0] = "检查纸张类型";
                        break;
                    case 29:
                        errorMsg[0] = "RFID标签未进行写入操作";
                        break;
                    case 30:
                        errorMsg[0] = "不支持浓度设置";
                        break;
                    case 31:
                        errorMsg[0] = "不支持的打印模式";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onCancelJob(boolean isSuccess) {
                Log.d("测试打印", "onCancelJob  isSuccess " + isSuccess);
            }

            @Override
            public void onBufferFree(int pageIndex, int bufferSize) {
                Log.d("测试打印", "onBufferFree");
                Log.d("测试打印", "isError[0] :  " + isError[0]);
                //pageIndex下一页的打印索引，bufferSize缓存控件
                if (isError[0]) {
                    result.success(-1);
                    return;
                }

                Log.d("测试打印", "pageIndex > 1 :  " + (pageIndex > 1));
                if (pageIndex > 1) {
                    result.success(1);
                    return;
                }

                float width = Float.parseFloat(parameter.get("width").toString());
                float height = Float.parseFloat(parameter.get("height").toString());
                int orientation = 0;

                /*
                 * 设置画布大小
                 *
                 * @param width 画布宽度
                 * @param height 画布高度
                 * @param orientation 画布旋转角度
                 * @param fontDir 字体路径暂不可用，默认""即可
                 *
                 */
                PrintUtil.getInstance().drawEmptyLabel(width, height, orientation, "");

                List subList = (List) parameter.get("subList");
                for (Object mapObj : subList) {
                    Map map = (Map) mapObj;
                    if (Objects.equals("商品条码", map.get("key").toString())) {
                        PrintUtil.getInstance().drawLabelBarCode(
                                Float.parseFloat(map.get("xaxis").toString()),
                                Float.parseFloat(map.get("yaxis").toString()),
                                Float.parseFloat(map.get("width").toString()),
                                Float.parseFloat(map.get("height").toString()),
                                20,
                                map.get("value").toString(),
                                3.2f,
                                0,
                                3,
                                0
                        );
                    } else {
                        PrintUtil.getInstance().drawLabelText(
                                Float.parseFloat(map.get("xaxis").toString()),
                                Float.parseFloat(map.get("yaxis").toString()),
                                Float.parseFloat(map.get("width").toString()),
                                Float.parseFloat(map.get("height").toString()),
                                map.get("value").toString(),
                                map.get("font").toString(),
                                Float.parseFloat(map.get("fontSize").toString()),
                                Integer.parseInt(map.get("textAlignHorizontal").toString()),
                                0, 1, 6, 0, 1, new boolean[]{false, false, false, false});
                    }
                }

                //生成打印数据
                byte[] jsonByte = PrintUtil.getInstance().generateLabelJson();

                //转换为jsonStr
                String jsonStr = new String(jsonByte);
                ArrayList<String> jsonList = new ArrayList<>();
                jsonList.add(jsonStr);
                //除B32/Z401/T8的printMultiple为11.81，其他的为8
                String jsonInfo = "{  " +
                        "\"printerImageProcessingInfo\": " + "{    " +
                        "\"orientation\":" + orientation + "," +
                        "   \"margin\": [      0,      0,      0,      0    ], " +
                        "   \"printQuantity\": " + quantity + ",  " +
                        "  \"horizontalOffset\": 0,  " +
                        "  \"verticalOffset\": 0,  " +
                        "  \"width\":" + width + "," +
                        "   \"height\":" + height + "," +
                        "\"printMultiple\":" + printMultiple + "," +
                        "  \"epc\": \"\"  }}";
                ArrayList<String> infoList = new ArrayList<>();
                infoList.add(jsonInfo);

                Log.d("测试打印", "PrintUtil.getInstance().commitData(jsonList,infoList) start");

                PrintUtil.getInstance().commitData(jsonList, infoList);

                Log.d("测试打印", "PrintUtil.getInstance().commitData(jsonList,infoList) end");
            }
        });

    }

    public void openPrinter(MethodCall call, MethodChannel.Result result) {
        String parameter = call.arguments();
        int ret = PrintUtil.openPrinter(parameter);
        result.success(ret);
    }

    public void closePrinter(MethodChannel.Result result) {
        PrintUtil.close();
        result.success(null);
    }

    public void queryBlueDevice(MethodChannel.Result result) {
        List<Map<String, Object>> ret = new ArrayList<>();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            Map<String, Object> map = new HashMap<>();
            map.put("deviceName", device.getName());
            map.put("deviceAddress", device.getAddress());
            ret.add(map);
        }
        result.success(ret);
    }
}
