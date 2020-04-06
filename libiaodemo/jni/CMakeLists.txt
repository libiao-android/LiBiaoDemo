# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html
# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/src/libs/${ANDROID_ABI})

#指定编译类型
set(CMAKE_BUILD_TYPE Release)

#参考https://stackoverflow.com/questions/38675403/how-to-config-cmake-for-strip-file
#去掉C和C++编译的调试信息减少so体积，即去掉符号表和重定向信息，无法断点调试，若需要请注释后使用
#set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -s")
#set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -s")

#打印log
message(STATUS "输出=：" ${CMAKE_BUILD_TYPE})

# Creates and names a library, sets it as either STATIC # or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.

    jnilib

# Sets the library as a shared library.

    SHARED

# Provides a relative path to your source file(s).

        src/main/cpp/main.cpp
        src/main/jniLibs/ffmpeg/ffmpeg.c
        src/main/jniLibs/ffmpeg/cmdutils.c
        src/main/jniLibs/ffmpeg/ffmpeg_filter.c
        src/main/jniLibs/ffmpeg/ffmpeg_hw.c
        src/main/jniLibs/ffmpeg/ffmpeg_opt.c
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.

       log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.

        log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

add_library( libffmpeg
        SHARED
        IMPORTED )

set_target_properties( libffmpeg
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libffmpeg.so)


# 添加ffmpeg头文件路径
include_directories(src/main/jniLibs/ffmpeg/)

target_link_libraries( # Specifies the target library.

        jnilib
        libffmpeg
       # Links the target library to the log library
       # included in the NDK.
       ${log-lib} )
