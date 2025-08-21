package BuenSabor.dto.preferenceMp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceItemDTO {
    private String id;
    private String title;
    private String description;
    private String pictureUrl;
    private String categoryId;
    private Integer quantity;
    private String currencyId;
    private BigDecimal unitPrice;
}
