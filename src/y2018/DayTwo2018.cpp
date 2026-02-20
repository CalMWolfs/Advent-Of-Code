#include "DayTwo2018.h"

#include <chrono>
#include <iostream>
#include <fstream>
#include <map>
#include <vector>

void DayTwo2018::solution() {
    std::ifstream file("resources/2018/box_ids.txt");

    if (!file.is_open()) {
        std::cerr << "Error opening file";
        return;
    }

    std::vector<std::string> lines;
    std::string readingLine;
    while (std::getline(file, readingLine)) {
        lines.push_back(readingLine);
    }

    const auto startTime = std::chrono::high_resolution_clock::now();

    std::string total;

    size_t curr = 1;

    for (std::string &str: lines) {
        for (size_t i = curr; i < lines.size(); i++) {
            std::string &target = lines[i];

            int differences = 0;
            for (size_t j = 0; j < target.size(); j++) {
                if (str[j] == target[j]) continue;
                differences++;
                if (differences == 2) break;
            }

            if (differences == 1) {
                for (size_t j = 0; j < target.size(); j++) {
                    if (str[j] != target[j]) continue;
                    total += str[j];
                }

                break;
            }
        }
        if (!total.empty()) break;
        curr++;
    }

    for (std::string &str: lines) {
        std::map<char, int> letterCounts;

        for (char c: str) {
            letterCounts[c]++;
        }
    }

    const auto endTime = std::chrono::high_resolution_clock::now();
    const auto totalNs = std::chrono::duration_cast<std::chrono::nanoseconds>(
        endTime - startTime
    ).count();

    std::cout << "totalNS: " << totalNs << "\n";
    std::cout << "total: " << total << "\n";
}
