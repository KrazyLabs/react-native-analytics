package com.smore.RNSegmentIOAnalytics;

import androidx.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.segment.analytics.Analytics;
import com.segment.analytics.Options;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;
import com.segment.analytics.ValueMap;

public class RNSegmentIOAnalyticsModule extends ReactContextBaseJavaModule {
  private Boolean mEnabled = true;

  @Override
  public String getName() {
    return "RNSegmentIOAnalytics";
  }

  private void log(String message) {
    Log.d("RNSegmentIOAnalytics", message);
  }

  public RNSegmentIOAnalyticsModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  /*
   https://segment.com/docs/libraries/android/#identify
   */
  @ReactMethod
  public void identify(String userId, ReadableMap traits, @Nullable ReadableMap options) {
    if (!mEnabled) {
      return;
    }
    Analytics.with(getReactApplicationContext().getApplicationContext()).identify(userId, toTraits(traits), toOptions(options));
  }

  /*
   https://segment.com/docs/libraries/android/#track
   */
  @ReactMethod
  public void track(String trackText, ReadableMap properties, @Nullable ReadableMap options) {
    if (!mEnabled) {
      return;
    }
    Analytics.with(getReactApplicationContext().getApplicationContext()).track(trackText, toProperties(properties), toOptions(options));
  }

  /*
   https://segment.com/docs/libraries/android/#screen
   */
  @ReactMethod
  public void screen(String screenName, ReadableMap properties, @Nullable ReadableMap options) {
    if (!mEnabled) {
      return;
    }
    Analytics.with(getReactApplicationContext().getApplicationContext()).screen(null, screenName, toProperties(properties), toOptions(options));
  }

  /*
   https://segment.com/docs/connections/sources/catalog/libraries/mobile/android/#anonymousid
   */
  @ReactMethod
  public void anonymousId(Promise promise) {
    if (!mEnabled) {
      promise.resolve("");
    } else {
      promise.resolve(Analytics.with(getReactApplicationContext().getApplicationContext()).getAnalyticsContext().traits().anonymousId());
    }
  }

  /*
   https://segment.com/docs/libraries/android/#flushing
   */
  @ReactMethod
  public void flush() {
    Analytics.with(getReactApplicationContext().getApplicationContext()).flush();
  }

  /*
   https://segment.com/docs/libraries/android/#reset
   */
  @ReactMethod
  public void reset() {
    Analytics.with(getReactApplicationContext().getApplicationContext()).reset();
  }

  /*
   https://segment.com/docs/libraries/android/#opt-out
   */
  @ReactMethod
  public void disable() {
    mEnabled = false;
  }

  /*
   https://segment.com/docs/libraries/android/#opt-out
   */
  @ReactMethod
  public void enable() {
    mEnabled = true;
  }

  private Properties toProperties (ReadableMap map) {
    Properties props = new Properties();
    addToValueMap(map, props);
    return props;
  }

  private void addToValueMap(ReadableMap map, ValueMap valueMap) {
    if (map == null) {
      return;
    }

    ReadableMapKeySetIterator iterator = map.keySetIterator();
    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      ReadableType type = map.getType(key);
      switch (type){
        case Array:
          valueMap.putValue(key, map.getArray(key));
          break;
        case Boolean:
          valueMap.putValue(key, map.getBoolean(key));
          break;
        case Map:
          valueMap.putValue(key, map.getMap(key));
          break;
        case Null:
          valueMap.putValue(key, null);
          break;
        case Number:
          valueMap.putValue(key, map.getDouble(key));
          break;
        case String:
          valueMap.putValue(key, map.getString(key));
          break;
        default:
          log("Unknown type:" + type.name());
          break;
      }
    }
  }

  private Traits toTraits (ReadableMap map) {
    Traits traits = new Traits();
    addToValueMap(map, traits);
    return traits;
  }

  private Options toOptions (ReadableMap map) {
    Options options = new Options();

    if (map == null) {
      return options;
    }

    ReadableMapKeySetIterator iterator = map.keySetIterator();
    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      switch (key) {
        case "integrations":
          ReadableType type = map.getType(key);
          switch (type) {
            case Map:
              ReadableMap integrationMap = map.getMap(key);
              ReadableMapKeySetIterator integrationIterator = integrationMap.keySetIterator();
              while (integrationIterator.hasNextKey()) {
                String integrationKey = integrationIterator.nextKey();
                ReadableType integrationType = integrationMap.getType(integrationKey);
                switch (integrationType) {
                  case Boolean:
                    options.setIntegration(integrationKey, integrationMap.getBoolean(integrationKey));
                    break;
                  default:
                    log("Unknown type (" + type.name() + ") for key: " + key);
                    break;
                }
              }
              break;
            default:
              log("Unknown type (" + type.name() + ") for key: " + key);
              break;
          }
          break;
        default:
          log("Unknown option: " + key);
          break;
      }
    }


    return options;
  }
}
