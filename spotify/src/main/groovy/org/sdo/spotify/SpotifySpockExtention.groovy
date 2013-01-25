package org.sdo.spotify

import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.IterationInfo
import org.spockframework.runtime.model.SpecInfo

public class SpotifySpockExtention extends AbstractAnnotationDrivenExtension<RunSpotify> {

	@Override
	public void visitSpecAnnotation(final RunSpotify annotation, SpecInfo sp) {
		def spotify_ps
		sp.addListener(new AbstractRunListener() {

					@Override
					public void afterFeature(FeatureInfo feature) {
						spotify_ps.waitForOrKill(100)
					}

					@Override
					public void beforeFeature(FeatureInfo feature) {
						spotify_ps = "spotify".execute()
					}
				})
	}
}
