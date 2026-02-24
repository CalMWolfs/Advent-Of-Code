cmake_minimum_required(VERSION 4.1)
project(Advent LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

add_compile_options(
        -Wall
        -Wextra
        -Werror
        -pedantic-errors
        -Wconversion
        -Wsign-conversion
        -Wdouble-promotion
        -Wcast-align
        -Wformat=2
        -Wuninitialized
        -Wnull-dereference
        -Wnon-virtual-dtor
        -Woverloaded-virtual
        -Wdeprecated-copy-dtor
        -Wold-style-cast
        #    -Wsuggest-override
        -fstack-protector-strong
)

set(CMAKE_CXX_FLAGS_DEBUG_INIT "-fsanitize=address,undefined")
set(CMAKE_CXX_FLAGS_RELWITHDEBINFO_INIT "-fsanitize=address,undefined")
set(CMAKE_CXX_EXE_LINKER_FLAGS_DEBUG_INIT "-fsanitize=address,undefined")
set(CMAKE_CXX_EXE_LINKER_FLAGS_RELWITHDEBINFO_INIT "-fsanitize=address,undefined")

enable_testing()

include(FetchContent)
FetchContent_Declare(
        googletest
        URL https://github.com/google/googletest/archive/refs/tags/v1.17.0.zip
)

set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)

FetchContent_MakeAvailable(googletest)

add_executable(Advent src/main.cpp
        src/y2018/DayOne2018.cpp
        src/y2018/DayOne2018.h
        src/y2018/DayTwo2018.cpp
        src/y2018/DayTwo2018.h
        src/y2018/DayThree2018.cpp
        src/y2018/DayThree2018.h
        src/y2018/DayFour2018.cpp
        src/y2018/DayFour2018.h
        src/y2018/DayFive2018.cpp
        src/y2018/DayFive2018.h
        src/y2018/DaySix2018.cpp
        src/y2018/DaySix2018.h
        src/y2018/DaySeven2018.cpp
        src/y2018/DaySeven2018.h)

target_link_libraries(Advent gtest_main)

include(CTest)
include(GoogleTest)
gtest_discover_tests(Advent)
