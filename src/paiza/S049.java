package paiza;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class S049 extends BaseSolution {


    @Run({"61", "30", "10", "30"})
    public int solve(int N, int X, int F, int S) {
        PriorityQueue<State> states = new PriorityQueue<>();
        states.offer(new State(N, X, 0));
        Map<Integer, State> minStates = new HashMap<>();
        int min = Integer.MAX_VALUE;
        while (!states.isEmpty()) {
            State state = states.poll();
            if (X > state.codeAbility) {
                int newCodeAbility = Math.min(X, state.codeAbility + S);
                State newState = new State(state.codeLeft, newCodeAbility, state.time + 3);
                processNewState(states, minStates, state, newState);
            }
            State newState = new State(state.codeLeft - state.codeAbility, Math.max(0, state.codeAbility - F),
                    state.time + 1);
            if (newState.codeLeft <= 0) {
                min = Math.min(newState.time, min);
                continue;
            }
            processNewState(states, minStates, state, newState);
        }
        return min;
    }

    private void processNewState(PriorityQueue<State> states, Map<Integer, State> minStates, State currentState, State newState){
        State minState = minStates.get(newState.time);
        if (minState != null) {
            if (!(minState.codeAbility >= newState.codeAbility && minState.codeLeft <= newState.codeLeft)) {
                states.offer(newState);
                if (newState.codeAbility >= minState.codeAbility && currentState.codeLeft <= minState.codeLeft) {
                    minStates.put(newState.time, newState);
                }
            }
        } else {
            states.offer(newState);
            minStates.put(newState.time, newState);
        }
    }

    static class State implements Comparable<State> {
        int codeLeft;
        int codeAbility;
        int time;

        public State(int codeLeft, int codeAbility, int time) {
            this.codeLeft = codeLeft;
            this.codeAbility = codeAbility;
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return codeLeft == state.codeLeft && codeAbility == state.codeAbility && time == state.time;
        }

        @Override
        public int hashCode() {
            return Objects.hash(codeLeft, codeAbility, time);
        }

        @Override
        public int compareTo(State o) {
            return this.time - o.time;
        }
    }
}
