#include "DaySix2018.h"

#include <chrono>
#include <iostream>
#include <fstream>
#include <map>
#include <set>
#include <vector>

class Coordinate {
public:
    int id;
    size_t x;
    size_t y;

    Coordinate(const std::string &str, const int number) {
        id = number;
        const size_t splitPos = str.find(',');
        x = static_cast<size_t>(std::stoi(str.substr(0, splitPos)));
        y = static_cast<size_t>(std::stoi(str.substr(splitPos + 1)));
    }
};

int manhattanDistance(size_t x1, size_t y1, size_t x2, size_t y2) {
    return std::abs(static_cast<int>(x1) - static_cast<int>(x2)) +
           std::abs(static_cast<int>(y1) - static_cast<int>(y2));
}

void DaySix2018::solution() {
    std::ifstream file("resources/2018/coordinates.txt");

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

    int total = 0;

    std::vector<Coordinate> coordinates;

    int id = 0;
    for (const std::string &line: lines) {
        coordinates.emplace_back(line, ++id);
    }

    size_t maxX = 0;
    size_t maxY = 0;

    for (auto &coordinate: coordinates) {
        maxX = std::max(maxX, coordinate.x + 1);
        maxY = std::max(maxY, coordinate.y + 1);
    }

    for (size_t y = 0; y < maxY; ++y) {
        for (size_t x = 0; x < maxX; ++x) {
            int totalDistance = 0;

            for (const auto &coordinate: coordinates) {
                const int dist = manhattanDistance(coordinate.x, coordinate.y, x, y);
                totalDistance += dist;
            }
            if (totalDistance < 10000) {
                total++;
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
