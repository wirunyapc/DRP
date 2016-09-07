package com.drpweb.util;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.kernel.solver.variables.integer.IntDomainVar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 9/5/2016.
 */
public class DailyDiet {
    IntegerVariable[][] varIds;
    IntegerVariable[][] varKals;
    IntegerVariable[][] varFats;
    IntegerVariable[][] varPros;
    IntegerVariable[][] varCarboes;
    public ArrayList<ArrayList<String[]>> plan;

    public DailyDiet() {
    }

    public Solver solve(int num, int prd, int[] arr_id, int[] arr_kal, int[] arr_fat, int[] arr_carboh,
                        int[] arr_protein, ArrayList<int[]> kals, ArrayList<int[]> fats,
                        ArrayList<int[]> carbohs, ArrayList<int[]> proteins) {
        CPModel m = new CPModel();
        CPSolver s = new CPSolver();
        this.varIds = new IntegerVariable[num][3];
        this.varKals = new IntegerVariable[num][3];
        this.varFats = new IntegerVariable[num][3];
        this.varPros = new IntegerVariable[num][3];
        this.varCarboes = new IntegerVariable[num][3];

        int intVars;
        int idx;
        for(intVars = 0; intVars < num; ++intVars) {
            for(idx = 0; idx < 3; ++idx) {
                this.varIds[intVars][idx] = Choco.makeIntVar("id_" + intVars + "_" + idx, arr_id, new String[0]);
                this.varKals[intVars][idx] = Choco.makeIntVar("kal_" + intVars + "_" + idx, arr_kal, new String[0]);
                this.varFats[intVars][idx] = Choco.makeIntVar("fat_" + intVars + "_" + idx, arr_fat, new String[0]);
                this.varPros[intVars][idx] = Choco.makeIntVar("pro_" + intVars + "_" + idx, arr_protein, new String[0]);
                this.varCarboes[intVars][idx] = Choco.makeIntVar("carbo_" + intVars + "_" + idx, arr_carboh, new String[0]);
            }
        }

        for(intVars = 0; intVars < num; ++intVars) {
            for(idx = 0; idx < 3; ++idx) {
                m.addConstraint(Choco.feasPairAC("cp:ac32", this.varIds[intVars][idx], this.varKals[intVars][idx], kals));
                m.addConstraint(Choco.feasPairAC("cp:ac32", this.varIds[intVars][idx], this.varFats[intVars][idx], fats));
                m.addConstraint(Choco.feasPairAC("cp:ac32", this.varIds[intVars][idx], this.varPros[intVars][idx], proteins));
                m.addConstraint(Choco.feasPairAC("cp:ac32", this.varIds[intVars][idx], this.varCarboes[intVars][idx], carbohs));
            }
        }

        for(intVars = 0; intVars < num; ++intVars) {
            m.addConstraint(Choco.and(new Constraint[]{Choco.leq(Choco.sum(this.varKals[intVars]), 2000), Choco.geq(Choco.sum(this.varKals[intVars]), 1200)}));
            m.addConstraint(Choco.leq(Choco.sum(this.varFats[intVars]), 33));
            m.addConstraint(Choco.and(new Constraint[]{Choco.leq(Choco.sum(this.varPros[intVars]), 60), Choco.geq(Choco.sum(this.varPros[intVars]), 45)}));
            m.addConstraint(Choco.and(new Constraint[]{Choco.leq(Choco.sum(this.varCarboes[intVars]), 165), Choco.geq(Choco.sum(this.varCarboes[intVars]), 150)}));
        }

        ArrayList var21 = new ArrayList();
        idx = 0;

        int i;
        for(i = 0; i < num; ++i) {
            for(int j = 0; j < 3; ++j) {
                var21.add(this.varIds[i][j]);
            }

            ++idx;
            if(idx % prd == 0) {
                m.addConstraint(Choco.allDifferent((IntegerVariable[])var21.toArray(new IntegerVariable[var21.size()])));
                var21.clear();
            }
        }

        if(var21.size() > 0) {
            m.addConstraint(Choco.allDifferent((IntegerVariable[])var21.toArray(new IntegerVariable[var21.size()])));
        }

        s.read(m);
        s.setTimeLimit(10000);
        s.solve();
        System.out.println("====================================================");
//        plan = new ArrayList<>();
        for(i = 0; i < num; ++i) {
//            plan.set(i, new ArrayList<>());
//            ArrayList<String[]> day = new ArrayList<>();
//            IntDomainVar[] idVars = s.getVar(this.varIds[i]);
//            IntDomainVar[] kcalVars = s.getVar(this.varKals[i]);
//            IntDomainVar[] fatVars = s.getVar(this.varFats[i]);
//            IntDomainVar[] proVars = s.getVar(this.varPros[i]);
//            IntDomainVar[] carboVars = s.getVar(this.varCarboes[i]);
//            int count = idVars.length;
//            for(int l = 0;l < count; l++){
//                String[] data = new String[5];
//                data[0] = idVars[l].toString();
//                data[1] = kcalVars[l].toString();
//                data[2] = fatVars[l].toString();
//                data[3] = proVars[l].toString();
//                data[4] = carboVars[l].toString();
//                day.set(l,data);
//            }
//            plan.add(day);

            IntDomainVar[] var22 = s.getVar(this.varIds[i]);
            int var18 = var22.length;
            int var19;
            IntDomainVar var;
            for(var19 = 0; var19 < var18; ++var19) {
                var = var22[var19];
                System.out.print(var + "       || ");
            }

            System.out.println();
            var22 = s.getVar(this.varKals[i]);
            var18 = var22.length;

            for(var19 = 0; var19 < var18; ++var19) {
                var = var22[var19];
                System.out.print(var + "    || ");
            }

            System.out.println();
            var22 = s.getVar(this.varFats[i]);
            var18 = var22.length;

            for(var19 = 0; var19 < var18; ++var19) {
                var = var22[var19];
                System.out.print(var + "     || ");
            }

            System.out.println();
            var22 = s.getVar(this.varPros[i]);
            var18 = var22.length;

            for(var19 = 0; var19 < var18; ++var19) {
                var = var22[var19];
                System.out.print(var + "     || ");
            }

            System.out.println();
            var22 = s.getVar(this.varCarboes[i]);
            var18 = var22.length;

            for(var19 = 0; var19 < var18; ++var19) {
                var = var22[var19];
                System.out.print(var + "  || ");
            }

            System.out.println();
            System.out.println("====================================================");
        }

        return s;
    }

    public List<IntegerVariable[][]> getVars() {
        ArrayList vars = new ArrayList();
        vars.add(this.varIds);
        vars.add(this.varKals);
        vars.add(this.varFats);
        vars.add(this.varPros);
        vars.add(this.varCarboes);
        return vars;
    }

    public String calBMI(double weight, double height) {
        double bmi = weight / (height / 100.0D * (height / 100.0D));
        DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
        bmi = (new Double(df.format(bmi))).doubleValue();
        return bmi < 18.5D?bmi + "(ผอม)":(18.5D <= bmi && bmi <= 22.9D?bmi + " (ปกติ)":(23.0D <= bmi && bmi <= 24.9D?bmi + " (อ้วนเล็กน้อย)":(25.0D <= bmi && bmi <= 29.9D?bmi + " (อ้วนปานกลาง)":(bmi >= 30.0D?bmi + " (อ้วนมาก)":"Value not found"))));
    }
}
