#include "DayFive2018.h"

#include <chrono>
#include <iostream>
#include <fstream>

void DayFive2018::solution() {
    std::ifstream file("resources/2018/polymer.txt");

    if (!file.is_open()) {
        std::cerr << "Error opening file";
        return;
    }

    const std::string original{std::istreambuf_iterator(file), std::istreambuf_iterator<char>()};

    file.close();

    const auto startTime = std::chrono::high_resolution_clock::now();

    size_t total = 0;

    size_t lowest = INT_MAX;

    for (int i = 97; i < 123; ++i) {
        std::string text = original;
        size_t left = 0;
        while (text.length() != left + 1) {
            const char current = text[left];

            if (std::tolower(current) == i) {
                text.erase(left, 1);
                continue;
            }

            if (left == 0) {
                left++;
                continue;
            }

            const char prev = text[left - 1];
            if (prev == current) {
                left++;
                continue;
            }
            if (std::tolower(prev) == std::tolower(current)) {
                text.erase(left - 1, 2);
                left--;
            } else {
                left++;
            }
        }
        if (std::tolower(text.back()) == i) {
            text.erase(left, 1);
        }

        lowest = std::min(lowest, text.size());
    }

    total = lowest;

    const auto endTime = std::chrono::high_resolution_clock::now();
    const auto totalNs = std::chrono::duration_cast<std::chrono::nanoseconds>(
        endTime - startTime
    ).count();

    std::cout << "totalNS: " << totalNs << "\n";
    std::cout << "total: " << total << "\n";
}
