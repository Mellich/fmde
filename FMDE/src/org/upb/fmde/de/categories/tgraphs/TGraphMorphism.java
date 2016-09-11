package org.upb.fmde.de.categories.tgraphs;

import static org.upb.fmde.de.categories.graphs.Graphs.Graphs;

import org.upb.fmde.de.categories.ComparableArrow;
import org.upb.fmde.de.categories.LabelledArrow;
import org.upb.fmde.de.categories.graphs.GraphMorphism;

public class TGraphMorphism extends LabelledArrow<TGraph> implements ComparableArrow<TGraphMorphism> {

	private GraphMorphism f;
	
	public TGraphMorphism(String label, GraphMorphism f, TGraph source, TGraph target) {
		super(label, source, target);
		this.f = f;
		if(!isValid()) throw new IllegalArgumentException("Typed GraphMorphism " + label + ": " + f.getSource().label() + " -> " + f.getTarget().label() + " is not valid.");
	}

	private boolean isValid() {
		return Graphs.compose(f, target.getTypeMorphism())
				     .isTheSameAs(source.getTypeMorphism());
	}

	public GraphMorphism getUntypedMorphism(){
		return f;
	}
	
	@Override
	public void label(String label) {
		super.label(label);
		f.label(label);
	}
	
	@Override
	public boolean isTheSameAs(TGraphMorphism a) {
		return source.getTypeMorphism().isTheSameAs(a.source.getTypeMorphism()) &&
			   target.getTypeMorphism().isTheSameAs(a.getTarget().getTypeMorphism()) &&
			   f.isTheSameAs(a.f);
	}
}
