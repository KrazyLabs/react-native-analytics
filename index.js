/**
 * @flow
 *
 * Created by Tal Kain <tal@kain.net>,
 * and Ricky Reusser <rsreusser@gmail.com>,
 * and Alex Rothberg
 * and Tony Xiao
 * Copyright (c) 2016 Fire Place Inc. All rights reserved.
 */
'use strict'

import {NativeModules} from 'react-native'

const NativeRNSegmentIOAnalytics = NativeModules.RNSegmentIOAnalytics

/**
 * RNSegmentIOAnalytics is a React Native wrapper for the Segment.com Analytics SDK.
 */

export default {
    /*
     * https://segment.com/docs/libraries/ios/#identify
     * https://segment.com/docs/libraries/android/#identify
     */
    identify: function (userId: string, traits: ?Object, options: ?Object) {
        NativeRNSegmentIOAnalytics.identify(userId, traits || {}, options || {})
    },

    /*
     * https://segment.com/docs/libraries/ios/#track
     * https://segment.com/docs/libraries/android/#track
     */
    track: function (event: string, properties: ?Object, options: ?Object) {
        NativeRNSegmentIOAnalytics.track(event, properties || {}, options || {})
    },

    /*
     * https://segment.com/docs/libraries/ios/#screen
     * https://segment.com/docs/libraries/android/#screen
     */
    screen: function (screenName: string, properties: ?Object, options: ?Object) {
        NativeRNSegmentIOAnalytics.screen(screenName, properties || {}, options || {})
    },

    /*
     * https://segment.com/docs/connections/sources/catalog/libraries/mobile/ios/#anonymousid
     * https://segment.com/docs/connections/sources/catalog/libraries/mobile/android/#anonymousid
     */
    anonymousId: function () {
        return NativeRNSegmentIOAnalytics.anonymousId()
    },

    /*
     * https://segment.com/docs/libraries/ios/#reset
     * https://segment.com/docs/libraries/android/#how-do-you-handle-unique-identifiers-
     */
    reset: function () {
        NativeRNSegmentIOAnalytics.reset()
    },

    /*
     * https://segment.com/docs/libraries/ios/#flushing
     * https://segment.com/docs/libraries/android/#how-does-the-library-queue-api-calls-
     */
    flush: function () {
        NativeRNSegmentIOAnalytics.flush()
    },

   /*
     * https://segment.com/docs/libraries/ios/#opt-out
     * https://segment.com/docs/libraries/android/#context
     */
    disable: function () {
        NativeRNSegmentIOAnalytics.disable()
    },

    /*
     * https://segment.com/docs/libraries/ios/#opt-out
     * https://segment.com/docs/libraries/android/#context
     */
    enable: function () {
        NativeRNSegmentIOAnalytics.enable()
    },
}
