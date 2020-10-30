package alex.app.domen;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COUNTRY_CODE")
public class CountryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name="code", nullable = false)
    private String code;

    @Column(name="country", nullable = false)
    private String country;


}
