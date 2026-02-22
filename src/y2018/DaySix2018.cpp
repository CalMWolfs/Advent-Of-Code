#include "DaySix2018.h"

#include <chrono>
#include <iostream>
#include <fstream>
#include <vector>

class Coordinate {
public:
    size_t x;
    size_t y;

    explicit Coordinate(const std::string &str) {
        const size_t splitPos = str.find(',');
        x = static_cast<size_t>(std::stoi(str.substr(0, splitPos)));
        y = static_cast<size_t>(std::stoi(str.substr(splitPos + 1)));
    }
};

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

    for (const std::string &line: lines) {
        coordinates.emplace_back(line);
    }

    size_t maxX = 0;
    size_t maxY = 0;

    for (auto &coordinate: coordinates) {
        maxX = std::max(maxX, coordinate.x);
        maxY = std::max(maxY, coordinate.y);
    }

    std::vector grid(maxX, std::vector(maxY, 0));

    const auto endTime = std::chrono::high_resolution_clock::now();
    const auto totalNs = std::chrono::duration_cast<std::chrono::nanoseconds>(
        endTime - startTime
    ).count();

    std::cout << "totalNS: " << totalNs << "\n";
    std::cout << "total: " << total << "\n";
}
