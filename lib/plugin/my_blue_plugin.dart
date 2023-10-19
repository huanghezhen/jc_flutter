import 'package:flutter/services.dart';

class MyBluePlugin {
  static const MethodChannel _channel = MethodChannel('blue_plugin');

  static Future<dynamic> queryBlueDevice() async {
    return await _channel.invokeMethod('queryBlueDevice');
  }

  static Future<dynamic> openPrinter(String address) async {
    return await _channel.invokeMethod('openPrinter', address);
  }

  static Future<dynamic> closePrinter() async {
    return await _channel.invokeMethod('closePrinter');
  }

  static Future<dynamic> print(Map data) async {
    return await _channel.invokeMethod('print', data);
  }
}
