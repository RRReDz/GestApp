package com.mcteam.gestapp.Utils;

import com.mcteam.gestapp.Models.Commessa;

import java.util.Comparator;

/**
 * @author Created by Riccardo Rossi on 09/10/2016.
 */

public class ComparatorPool {

    public static Comparator<Commessa> getCommessaComparator() {
        return new Comparator<Commessa>() {
            @Override
            public int compare(Commessa lhs, Commessa rhs) {
                String lhsNomeSocieta = null;
                String rhsNomeSocieta = null;

                if (lhs.getCliente() != null && lhs.getCliente().getNomeSocietà() != null)
                    lhsNomeSocieta = lhs.getCliente().getNomeSocietà();
                if (rhs.getCliente() != null && rhs.getCliente().getNomeSocietà() != null)
                    rhsNomeSocieta = rhs.getCliente().getNomeSocietà();

                if (rhsNomeSocieta != null && lhsNomeSocieta != null)
                    return String.CASE_INSENSITIVE_ORDER.compare(lhsNomeSocieta, rhsNomeSocieta);
                if (lhsNomeSocieta != null && rhsNomeSocieta == null) {
                    return -1;
                } else
                    return 2;
            }
        };
    }
}
