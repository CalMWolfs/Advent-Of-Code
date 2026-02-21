#include "DayFour2018.h"

#include <algorithm>
#include <chrono>
#include <iostream>
#include <fstream>
#include <map>
#include <vector>

class NightLog {
public:
    int month;
    int day;
    int hour;
    int minute;
    std::string shiftInfo;

    bool operator<(const NightLog &other) const {
        return std::tie(month, day, hour, minute) < std::tie(other.month, other.day, other.hour, other.minute);
    }

    explicit NightLog(const std::string &str) {
        const size_t splitPos = str.find(']');
        const std::string dateInfo = str.substr(6, splitPos - 6);

        month = std::stoi(dateInfo.substr(0, 2));
        day = std::stoi(dateInfo.substr(3, 2));
        hour = std::stoi(dateInfo.substr(6, 2));
        minute = std::stoi(dateInfo.substr(9, 2));

        shiftInfo = str.substr(splitPos + 2);
    }
};

void DayFour2018::solution() {
    std::ifstream file("resources/2018/guard_logs.txt");

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

    std::vector<NightLog> logs;

    for (const std::string &line: lines) {
        logs.emplace_back(line);
    }

    int total = 0;

    std::sort(logs.begin(), logs.end());

    std::map<int, std::map<int, int> > data;
    int currentGuard = 0;
    int currentMinute = 0;

    for (auto &log: logs) {
        if (log.shiftInfo.starts_with("Guard")) {
            const std::string subStr = log.shiftInfo.substr(7);
            const size_t splitPos = subStr.find(' ');
            currentGuard = std::stoi(subStr.substr(0, splitPos));
        } else if (log.shiftInfo[0] == 'f') {
            currentMinute = log.minute;
        } else {
            for (int i = currentMinute; i < log.minute; ++i) {
                data[currentGuard][i]++;
            }
        }
    }

    int bestGuard = 0;
    int bestMinute = 0;
    int maxValue = INT_MIN;

    for (const auto &[guardNum, inner]: data) {
        for (const auto &[minute, amount]: inner) {
            if (amount > maxValue) {
                maxValue = amount;
                bestGuard = guardNum;
                bestMinute = minute;
            }
        }
    }

    total = bestGuard * bestMinute;

    const auto endTime = std::chrono::high_resolution_clock::now();
    const auto totalNs = std::chrono::duration_cast<std::chrono::nanoseconds>(
        endTime - startTime
    ).count();

    std::cout << "totalNS: " << totalNs << "\n";
    std::cout << "total: " << total << "\n";
}
