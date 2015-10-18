# Copyright 2004-present Facebook. All Rights Reserved.

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := gifimage

LOCAL_SRC_FILES := \
  jni.cpp \
  gif.cpp \
  jni_helpers.cpp \

CXX11_FLAGS := -std=c++11
LOCAL_CFLAGS += $(CXX11_FLAGS)
LOCAL_EXPORT_CPPFLAGS := $(CXX11_FLAGS)
LOCAL_LDLIBS += -ljnigraphics
ifeq ($(BUCK_BUILD), 1)
  LOCAL_CFLAGS += $(BUCK_DEP_CFLAGS)
  LOCAL_LDFLAGS += $(BUCK_DEP_LDFLAGS)
  include $(BUILD_SHARED_LIBRARY)
else
  LOCAL_SHARED_LIBRARIES += gif
  include $(BUILD_SHARED_LIBRARY)
  $(call import-module, giflib)
endif
