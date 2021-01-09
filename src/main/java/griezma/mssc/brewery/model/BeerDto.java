package griezma.mssc.brewery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data @Builder @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeerDto implements Serializable {
    static final long serialVersionUID = 7220034028643401220L;

    @Null
    private UUID id;
    @NotBlank
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @Null
    @JsonFormat(timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime created;
    @Null
    @JsonFormat(timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime lastModified;
    private String upc;
    @Positive
    @Max(200)
    private BigDecimal price;
    @Positive
    private Integer quantityOnHand;
}
