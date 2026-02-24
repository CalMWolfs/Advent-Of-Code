#include "DaySeven2018.h"

#include <chrono>
#include <iostream>
#include <fstream>
#include <map>
#include <set>
#include <vector>

void DaySeven2018::solution() {
    std::ifstream file("resources/2018/instruction_steps.txt");

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

    std::map<char, std::set<char> > requiredSteps;

    requiredSteps['A'] = std::set<char>();
    requiredSteps['B'] = std::set<char>();
    // requiredSteps['C'] = std::set<char>();
    requiredSteps['G'] = std::set<char>();
    requiredSteps['Y'] = std::set<char>();

    for (const std::string &line: lines) {
        const char requiredStep = line[5];
        const char currentStep = line[36];

        requiredSteps[currentStep].insert(requiredStep);
    }

    const int WORKER_COUNT = 5;
    const int TIME_OFFSET = 4;

    std::vector<std::pair<char, int> > workers;

    while (!requiredSteps.empty()) {
        for (size_t i = 0; i < workers.size(); ++i) {
            if (workers[i].second != total) continue;
            for (auto &[step2, required2]: requiredSteps) {
                required2.erase(workers[i].first);
            }
            workers.erase(workers.begin() + static_cast<int>(i));
        }

        if (workers.size() == WORKER_COUNT) {
            total++;
            continue;
        }

        std::set<char> toRemove;

        for (auto &[step, required]: requiredSteps) {
            if (workers.size() == WORKER_COUNT) continue;
            if (!required.empty()) continue;
            workers.emplace_back(step, total + (step - TIME_OFFSET));
            toRemove.insert(step);
        }

        for (const char &t: toRemove) {
            requiredSteps.erase(t);
        }

        total++;
    }

    while (!workers.empty()) {
        for (size_t i = 0; i < workers.size(); ++i) {
            if (workers[i].second != total) continue;
            for (auto &[step2, required2]: requiredSteps) {
                required2.erase(workers[i].first);
            }
            workers.erase(workers.begin() + static_cast<int>(i));
        }
        total++;
    }
    total--;

    for (const auto &[step, required]: requiredSteps) {
        std::cout << step << " requires: " << required.size() << " steps.\n";
    }

    const auto endTime = std::chrono::high_resolution_clock::now();
    const auto totalNs = std::chrono::duration_cast<std::chrono::nanoseconds>(
        endTime - startTime
    ).count();

    std::cout << "totalNS: " << totalNs << "\n";
    std::cout << "total: " << total << "\n";
}
