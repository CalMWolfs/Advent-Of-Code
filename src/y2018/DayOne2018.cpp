#include "DayOne2018.h"

#include <chrono>
#include <iostream>
#include <fstream>
#include <set>
#include <vector>

void DayOne2018::solution() {
    std::ifstream file("resources/2018/frequencies.txt");

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

    int total = INT_MIN;

    std::set<int> seenNumbers;
    int currentCount = 0;

    while (total == INT_MIN) {
        for (std::string &line: lines) {
            const char symbol = line[0];
            const int number = std::stoi(line.substr(1));
            if (symbol == '+') {
                currentCount += number;
            } else {
                currentCount -= number;
            }
            if (!seenNumbers.insert(currentCount).second) {
                total = currentCount;
                break;
            }
        }
    }

    const auto endTime = std::chrono::high_resolution_clock::now();
    const auto totalNs = std::chrono::duration_cast<std::chrono::nanoseconds>(
        endTime - startTime
    ).count();

    std::cout << "totalNS: " << totalNs << "\n";
    std::cout << "total: " << total << "\n";
}
