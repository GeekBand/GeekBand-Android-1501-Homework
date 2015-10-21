# Copyright 2004-present Facebook. All Rights Reserved.
APP_BUILD_SCRIPT := Android.mk

APP_ABI := armeabi-v7a armeabi x86
APP_PLATFORM := android-9

APP_MK_DIR := $(dir $(lastword $(MAKEFILE_LIST)))
NDK_MODULE_PATH := $(APP_MK_DIR):$(APP_MK_DIR)../../../build/nativemerge

APP_STL := gnustl_shared

# Make sure every shared lib includes a .note.gnu.build-id header
APP_LDFLAGS := -Wl,--build-id

NDK_TOOLCHAIN_VERSION := 4.8
