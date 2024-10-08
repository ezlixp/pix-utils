package pixlze.utils;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import pixlze.mod.PixUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Visitors {
    public static StringBuilder currentVisit;
    public static final StringVisitable.StyledVisitor<String> RAID_VISITOR = new StringVisitable.StyledVisitor<String>() {
        @Override
        public Optional<String> accept(Style style, String asString) {
            if (style.getFont().getPath().startsWith("hud")) {
                return "break".describeConstable();
            }
            if (style.getHoverEvent() != null) {
                try {
                    List<Text> onHover = null;
                    if (style.getHoverEvent().getValue(style.getHoverEvent().getAction()) != null) {
                        onHover = ((Text) Objects.requireNonNull(style.getHoverEvent().getValue(style.getHoverEvent().getAction()))).getSiblings();
                    } else {
                        PixUtils.LOGGER.info("null hover event: {} in message {}", style, asString);
                    }
                    if (asString.indexOf('/') == -1) {
                        if (onHover != null && onHover.size() > 2 && onHover.get(1).getString() != null && Objects.requireNonNull(onHover.get(1).getString()).contains("'s nickname is ")) {
                            currentVisit.append(onHover.getFirst().getString());
                        } else {
                            currentVisit.append(asString.replaceAll("\\n", "\\\\n").replaceAll("§", "&"));
                        }
                    } else if (onHover == null || onHover.size() < 2 || onHover.get(1).getString() == null || !Objects.requireNonNull(onHover.get(1).getString()).contains("'s nickname is ")) {
                        currentVisit.append(asString.replaceAll("\\n", "\\\\n").replaceAll("§", "&"));
                    }
                } catch (Exception e) {
                    PixUtils.LOGGER.error("raid visitor hover error: {}", e.getMessage());
                }
            } else {
                if (style.getColor() != null) {
                    int colorIndex = 0;
                    for (Formatting format : Formatting.values()) {
                        if (format.getColorValue() != null && format.getColorValue().equals(style.getColor().getRgb())) {
                            colorIndex = format.getColorIndex();
                            break;
                        }
                    }
                    currentVisit.append("&").append(Objects.requireNonNull(Formatting.byColorIndex(colorIndex)).getCode());
                }
                if (style.isBold()) {
                    currentVisit.append("&").append(Formatting.BOLD.getCode());
                }
                if (style.isItalic()) {
                    currentVisit.append("&").append(Formatting.ITALIC.getCode());
                }
                if (style.isUnderlined()) {
                    currentVisit.append("&").append(Formatting.UNDERLINE.getCode());
                }
                if (style.isStrikethrough()) {
                    currentVisit.append("&").append(Formatting.STRIKETHROUGH.getCode());
                }
                if (style.isObfuscated()) {
                    currentVisit.append("&").append(Formatting.OBFUSCATED.getCode());
                }
                currentVisit.append(asString.replaceAll("\\n", "\\\\n").replaceAll("§", "&"));
            }
            return Optional.empty();
        }
    };
    public static final StringVisitable.StyledVisitor<String> PLAIN_VISITOR = new StringVisitable.StyledVisitor<>() {
        @Override
        public Optional<String> accept(Style style, String asString) {
            PixUtils.LOGGER.info("{} {}", style.getFont(), Identifier.of("default"));
            if (style.getFont().equals(Identifier.of("default"))) {
                PixUtils.LOGGER.info("here");
                currentVisit.append(asString.replaceAll("§.", ""));
            }
            return Optional.empty();
        }
    };
    public static final StringVisitable.StyledVisitor<String> STYLED_VISITOR = new StringVisitable.StyledVisitor<>() {
        @Override
        public Optional<String> accept(Style style, String asString) {
            if (style.getFont().getPath().startsWith("hud")) {
                return "break".describeConstable();
            }
            if (style.getColor() != null) {
                int colorIndex = 0;
                for (Formatting format : Formatting.values()) {
                    if (format.getColorValue() != null && format.getColorValue().equals(style.getColor().getRgb())) {
                        colorIndex = format.getColorIndex();
                        break;
                    }
                }
                currentVisit.append("&").append(Objects.requireNonNull(Formatting.byColorIndex(colorIndex)).getCode());
            }
            if (style.isBold()) {
                currentVisit.append("&").append(Formatting.BOLD.getCode());
            }
            if (style.isItalic()) {
                currentVisit.append("&").append(Formatting.ITALIC.getCode());
            }
            if (style.isUnderlined()) {
                currentVisit.append("&").append(Formatting.UNDERLINE.getCode());
            }
            if (style.isStrikethrough()) {
                currentVisit.append("&").append(Formatting.STRIKETHROUGH.getCode());
            }
            if (style.isObfuscated()) {
                currentVisit.append("&").append(Formatting.OBFUSCATED.getCode());
            }
            currentVisit.append(asString.replaceAll("\\n", "\\\\n").replaceAll("§", "&"));
            return Optional.empty();
        }
    };
}
