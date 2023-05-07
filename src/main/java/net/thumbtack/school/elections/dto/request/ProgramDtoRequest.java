package net.thumbtack.school.elections.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.elections.model.Sentence;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProgramDtoRequest {
    private Set<Sentence> program;

    public ProgramDtoRequest(Set<Sentence> program) {
        this.program = program;
    }
    public ProgramDtoRequest(){
        this(new HashSet<>());
    }
    public void addProgram(Sentence sentence){
        program.add(sentence);
    }
    public void deleteProgram(Sentence sentence){
        program.remove(sentence);
    }
}
