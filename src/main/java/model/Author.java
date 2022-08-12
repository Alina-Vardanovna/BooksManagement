package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ourEnum.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private String name;
    private String surname;
    private String gmail;
    private Gender gender;

}
