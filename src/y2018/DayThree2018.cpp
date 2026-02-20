#include "DayThree2018.h"

#include <chrono>
#include <iostream>
#include <fstream>
#include <set>
#include <vector>

class ClaimData {
public:
    int number;
    size_t startX;
    size_t startY;
    size_t width;
    size_t height;

    explicit ClaimData(const std::string &str) {
        std::istringstream iss(str);
        std::vector<std::string> split;
        std::string word;

        while (iss >> word) {
            split.push_back(word);
        }

        number = std::stoi(split[0].substr(1));
        size_t splitPos = split[2].find(',');
        startX = static_cast<size_t>(std::stoi(split[2].substr(0, splitPos)));
        startY = static_cast<size_t>(std::stoi(split[2].substr(splitPos + 1, split[2].length() - 1)));
        splitPos = split[3].find('x');
        width = static_cast<size_t>(std::stoi(split[3].substr(0, splitPos)));
        height = static_cast<size_t>(std::stoi(split[3].substr(splitPos + 1)));
    }
};

void DayThree2018::solution() {
    std::ifstream file("resources/2018/claims.txt");

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

    std::vector<ClaimData> claims;

    for (const std::string &line : lines) {
        claims.emplace_back(line);
    }

    std::vector matrix(1000, std::vector(1000, 0));

    std::set<int> untouchedClaims;

    for (const auto &claim : claims) {
        bool wasValid = true;
        for (size_t i = claim.startX; i < claim.startX + claim.width; ++i) {
            for (size_t j = claim.startY; j < claim.startY + claim.height; ++j) {
                if (int existing = matrix[i][j]; existing != 0) {
                    wasValid = false;
                    untouchedClaims.erase(existing);
                }

                matrix[i][j] = claim.number;
            }
        }
        if (wasValid) untouchedClaims.insert(claim.number);
    }

    for (int untouchedClaim : untouchedClaims) {
        total = untouchedClaim;
    }

    const auto endTime = std::chrono::high_resolution_clock::now();
    const auto totalNs = std::chrono::duration_cast<std::chrono::nanoseconds>(
        endTime - startTime
    ).count();

    std::cout << "totalNS: " << totalNs << "\n";
    std::cout << "total: " << total << "\n";
}
