package com.rysanek.sportsfandom.domain.glide

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideApplicationModule: AppGlideModule() {

    override fun isManifestParsingEnabled() = false
}