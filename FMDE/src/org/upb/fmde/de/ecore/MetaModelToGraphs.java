package org.upb.fmde.de.ecore;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.upb.fmde.de.categories.finsets.FinSet;
import org.upb.fmde.de.categories.finsets.TotalFunction;
import org.upb.fmde.de.categories.graphs.Graph;

public class MetaModelToGraphs {
	private Graph result;
	private FinSet vertices;
	private FinSet edges;
	private TotalFunction source;
	private TotalFunction target;

	public MetaModelToGraphs(EObject root, String label) {
		vertices = new FinSet("V_" + label);
		edges = new FinSet("E_" + label);
		source = new TotalFunction(edges, "s_" + label, vertices);
		target = new TotalFunction(edges, "t_" + label, vertices);

		traverseModel(root);
		result = new Graph(label, edges, vertices, source, target);
	}

	private void traverseModel(EObject root) {
		visitNode(root);
		root.eAllContents().forEachRemaining(eob -> visitNode(eob));

		visitEdges(root);
		root.eAllContents().forEachRemaining(eob -> visitEdges(eob));
	}

	private void visitEdges(EObject eob) {
		if(filterForRelevantEdgeTypes(eob))
			addEdge((EReference)eob);
	}

	private boolean filterForRelevantEdgeTypes(EObject eob) {
		return eob.eClass().equals(EcorePackage.eINSTANCE.getEReference());
	}

	private void addEdge(EReference edge) {
		edges.getElements().add(edge);
		source.addMapping(edge, edge.getEContainingClass());
		target.addMapping(edge, edge.getEReferenceType());
	}

	private void visitNode(EObject eob) {
		if (filterForRelevantNodeTypes(eob))
			vertices.getElements().add(eob);
	}

	private boolean filterForRelevantNodeTypes(EObject eob) {
		return eob.eClass().equals(EcorePackage.eINSTANCE.getEClass());
	}

	public Graph getResult() {
		return result;
	}
}
