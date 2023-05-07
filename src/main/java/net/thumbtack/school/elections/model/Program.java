package net.thumbtack.school.elections.model;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@Setter
@Getter
@Data
public class Program {
    private Set<Sentence> program;

    public Program(Set<Sentence> program) {
        this.program = program;
    }
    public Program(){
        this(new HashSet<>());
    }
    public void addProgram(Sentence sentence){
        program.add(sentence);
    }
    public void deleteProgram(Sentence sentence){
        program.remove(sentence);
    }
}
