package aoc.y2022;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Q19 extends BaseSolution {


    private static final ForkJoinPool POOL = new ForkJoinPool(16);

    @Run({"input-aoc/Q20221219.txt"})
    public int part1(String s) {
        Blueprint.MaxCollectTask.TIME_LIMIT = 24;
        List<Blueprint> blueprints = parseBlueprint(s);
        return blueprints.stream().map(Blueprint::qualityLevel).mapToInt(Integer::intValue).sum();
    }

    @Run({"input-aoc/Q20221219.txt"})
    public int part2(String s) throws InterruptedException {
        Blueprint.MaxCollectTask.TIME_LIMIT = 32;
        List<Blueprint> blueprints = parseBlueprint(s);
        return blueprints.stream().limit(3)
                .map(Blueprint::maxCollect)
                .mapToInt(Integer::intValue)
                .reduce(1, (x, y) -> x * y);
    }

    record Blueprint(int id, int oreRobotCost, int clayRobotCost, int obsidianRobotOreCost, int obsidianRobotClayCost,
                     int geodeRobotOreCost, int geodeRobotObsidianCost) {

        public int qualityLevel() {
            return maxCollect() * id;
        }

        public int maxCollect() {
            return POOL.invoke(new MaxCollectTask(1, 1, 0, 0, 0, 1, 0,
                    0, 0, false, false, false));
        }

        @AllArgsConstructor
        class MaxCollectTask extends RecursiveTask<Integer> {

            public static int TIME_LIMIT = 32;
            private final int time;
            private final int ore;
            private final int clay;
            private final int obsidian;
            private final int geode;
            private final int oreRobot;
            private final int clayRobot;
            private final int obsidianRobot;
            private final int geodeRobot;
            private final boolean lockOre;
            private final boolean lockClay;
            private final boolean lockObsidian;

            @Override
            protected Integer compute() {

                if (time >= TIME_LIMIT) return geode;
                List<ForkJoinTask<Integer>> tasks = new ArrayList<>(5);
                boolean canMakeOreRobot = ore >= oreRobotCost;
                boolean canMakeClayRobot = ore >= clayRobotCost;
                boolean canMakeObsidianRobot = ore >= obsidianRobotOreCost && clay >= obsidianRobotClayCost;

                tasks.add(new MaxCollectTask(time + 1, ore + oreRobot,
                        clay + clayRobot, obsidian + obsidianRobot, geode + geodeRobot,
                        oreRobot, clayRobot, obsidianRobot, geodeRobot, canMakeOreRobot, canMakeClayRobot, canMakeObsidianRobot).fork());
                if (canMakeOreRobot && !lockOre) {
                    tasks.add(new MaxCollectTask(time + 1, ore - oreRobotCost + oreRobot,
                            clay + clayRobot, obsidian + obsidianRobot, geode + geodeRobot,
                            oreRobot + 1, clayRobot, obsidianRobot, geodeRobot, false, false, false).fork());
                }
                if (canMakeClayRobot && !lockClay) {
                    tasks.add(new MaxCollectTask(time + 1, ore - clayRobotCost + oreRobot,
                            clay + clayRobot, obsidian + obsidianRobot, geode + geodeRobot,
                            oreRobot, clayRobot + 1, obsidianRobot, geodeRobot, false, false, false).fork());
                }
                if (canMakeObsidianRobot && !lockObsidian) {
                    tasks.add(new MaxCollectTask(time + 1, ore - obsidianRobotOreCost + oreRobot,
                            clay - obsidianRobotClayCost + clayRobot, obsidian + obsidianRobot,
                            geode + geodeRobot, oreRobot, clayRobot, obsidianRobot + 1, geodeRobot, false, false, false)
                            .fork());
                }
                if (ore >= geodeRobotOreCost && obsidian >= geodeRobotObsidianCost) {
                    tasks.add(new MaxCollectTask(time + 1, ore - geodeRobotOreCost + oreRobot,
                            clay + clayRobot, obsidian - geodeRobotObsidianCost + obsidianRobot,
                            geode + geodeRobot, oreRobot, clayRobot, obsidianRobot, geodeRobot + 1, false, false, false)
                            .fork());
                }
                return tasks.stream().map(this::joinWithSneakThrows).max(Comparator.naturalOrder()).orElse(geode);
            }

            @SneakyThrows
            private Integer joinWithSneakThrows(ForkJoinTask<Integer> task) {
                return task.join();
            }
        }
    }

    public List<Blueprint> parseBlueprint(String s) {
        Scanner scanner = new Scanner(s);
        List<Blueprint> blueprints = new ArrayList<>();
        int id = 1;
        while (scanner.hasNextLine()) {
            scanner.skip("Blueprint \\d+: Each ore robot costs ");
            int oreRobotCost = scanner.nextInt();
            scanner.skip(" ore. Each clay robot costs ");
            int clayRobotCost = scanner.nextInt();
            scanner.skip(" ore. Each obsidian robot costs ");
            int obsidianRobotOreCost = scanner.nextInt();
            scanner.skip(" ore and ");
            int obsidianRobotClayCost = scanner.nextInt();
            scanner.skip(" clay. Each geode robot costs ");
            int geodeRobotOreCost = scanner.nextInt();
            scanner.skip(" ore and ");
            int geodeRobotObsidianCost = scanner.nextInt();
            scanner.nextLine();
            blueprints.add(new Blueprint(id++, oreRobotCost, clayRobotCost, obsidianRobotOreCost, obsidianRobotClayCost,
                    geodeRobotOreCost, geodeRobotObsidianCost));
        }
        return blueprints;
    }
}
