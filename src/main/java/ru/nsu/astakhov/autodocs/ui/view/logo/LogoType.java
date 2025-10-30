package ru.nsu.astakhov.autodocs.ui.view.logo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LogoType {
    LOGO("/logo/%s_logo.png"),
    HORIZONTAL_LOGO("/logo/%s_logo_h.png"),
    LOGO_IMAGE("/logo/%s_logo_image.png"),
    TRANSPARENT_LOGO_IMAGE("/logo/transparent_logo_image.png");

    private final String path;
}
