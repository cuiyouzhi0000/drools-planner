/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.examples.nqueens.swingui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.drools.planner.core.move.Move;
import org.drools.planner.core.solution.Solution;
import org.drools.planner.examples.common.swingui.SolutionPanel;
import org.drools.planner.examples.nqueens.domain.NQueens;
import org.drools.planner.examples.nqueens.domain.Queen;
import org.drools.planner.examples.nqueens.domain.Row;
import org.drools.planner.examples.nqueens.solver.move.RowChangeMove;

/**
 * TODO this code is highly unoptimized
 */
public class NQueensPanel extends SolutionPanel {

    private static final String QUEEN_IMAGE_PATH = "/org/drools/planner/examples/nqueens/swingui/queenImage.png";

    private ImageIcon queenImageIcon;

    public NQueensPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        queenImageIcon = new ImageIcon(getClass().getResource(QUEEN_IMAGE_PATH));
    }

    private NQueens getNQueens() {
        return (NQueens) solutionBusiness.getSolution();
    }

    public void resetPanel(Solution solution) {
        removeAll();
        NQueens nQueens = (NQueens) solution;
        int n = nQueens.getN();
        List<Queen> queenList = nQueens.getQueenList();
        setLayout(new GridLayout(n, n));
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                Queen queen = queenList.get(x);
                if (queen.getColumn().getIndex() != x) {
                    throw new IllegalStateException("The queenList is not in the expected order.");
                }
                if (queen.getRow() != null && queen.getRow().getIndex() == y) {
                    JButton button = new JButton(new QueenAction(queen));
                    button.setHorizontalTextPosition(SwingConstants.CENTER);
                    button.setVerticalTextPosition(SwingConstants.BOTTOM);
                    add(button);
                } else {
                    JPanel panel = new JPanel();
                    panel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.DARK_GRAY),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                    Color background = (((y + x) % 2) == 0) ? Color.WHITE : Color.GRAY;
                    panel.setBackground(background);
                    add(panel);
                }
            }
        }
    }

    private class QueenAction extends AbstractAction {

        private Queen queen;

        public QueenAction(Queen queen) {
            super("[" + queen.getId() + "]", queenImageIcon);
            this.queen = queen;
        }

        public void actionPerformed(ActionEvent e) {
            List<Row> rowList = getNQueens().getRowList();
            JComboBox rowListField = new JComboBox(rowList.toArray());
            rowListField.setSelectedItem(queen.getRow());
            int result = JOptionPane.showConfirmDialog(NQueensPanel.this.getRootPane(), rowListField, "Select row",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Row toRow = (Row) rowListField.getSelectedItem();
                Move move = new RowChangeMove(queen, toRow);
                solutionBusiness.doMove(move);
                solverAndPersistenceFrame.resetScreen();
            }
        }

    }

}
