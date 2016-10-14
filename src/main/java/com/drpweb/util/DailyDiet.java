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
    public Solver solver;
    public int totalNum;

    public DailyDiet() {
    }

    public Solver solve(int num, int prd, int[] arr_id, int[] arr_kal, int[] arr_fat, int[] arr_carboh,
                        int[] arr_protein, ArrayList<int[]> kals, ArrayList<int[]> fats,
                        ArrayList<int[]> carbohs, ArrayList<int[]> proteins, int di_kcal, int di_fat, int di_prot, int di_carboh,int bmr,String mode) {
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
            if(mode.equals("member")) {
                System.out.println("Build member");
                m.addConstraint(Choco.and(new Constraint[]{Choco.leq(Choco.sum(this.varKals[intVars]), bmr), Choco.geq(Choco.sum(this.varKals[intVars]), 1200)}));
                m.addConstraint(Choco.leq(Choco.sum(this.varFats[intVars]), 33));
                m.addConstraint(Choco.and(new Constraint[]{Choco.leq(Choco.sum(this.varPros[intVars]), 60), Choco.geq(Choco.sum(this.varPros[intVars]), 45)}));
                m.addConstraint(Choco.and(new Constraint[]{Choco.leq(Choco.sum(this.varCarboes[intVars]), 165), Choco.geq(Choco.sum(this.varCarboes[intVars]), 150)}));
            }else{
                System.out.println("Build patient");
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(this.varKals[intVars]), bmr+500), Choco.geq(Choco.sum(this.varKals[intVars]), bmr-500)));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(this.varFats[intVars]), di_fat+10),  Choco.geq(Choco.sum(this.varKals[intVars]), di_fat-10)));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(this.varPros[intVars]), di_prot+5), Choco.geq(Choco.sum(this.varPros[intVars]), di_prot-20)));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(this.varCarboes[intVars]), di_carboh+5), Choco.geq(Choco.sum(this.varCarboes[intVars]), di_carboh-30)));



            }
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

            IntDomainVar[] var22 = s.getVar(this.varIds[i]);
            int var18 = var22.length;
            int var19;
            IntDomainVar var;
            for(var19 = 0; var19 < var18; ++var19) {
                var = var22[var19];
                System.out.print(var + "     || ");
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
        this.totalNum = num;
        this.solver = s;
        return s;
    }
    public String toJson(){
        String result = "[";
        System.out.println("====================================================");

        Solver s = this.solver;
        for(int i = 0; i < this.totalNum; ++i) {
            String meal = "[";

            IntDomainVar[] idVars = s.getVar(this.varIds[i]);
            IntDomainVar[] kcalVars = s.getVar(this.varKals[i]);
            IntDomainVar[] fatVars = s.getVar(this.varFats[i]);
            IntDomainVar[] proVars = s.getVar(this.varPros[i]);
            IntDomainVar[] carboVars = s.getVar(this.varCarboes[i]);
            int count = idVars.length;
            for(int l = 0;l < count; l++){
                String data = "[";

                data += "\"" + idVars[l].toString() + "\",";
                data += "\"" + kcalVars[l].toString() + "\",";
                data += "\"" + fatVars[l].toString() + "\",";
                data += "\"" + proVars[l].toString() + "\",";
                data += "\"" + carboVars[l].toString() + "\"";
                if((l+1) == count){
                    data += "]";
                }else{
                    data += "],";
                }

                meal += data;
            }
            if((i+1) == totalNum){
                meal += "]";
            }else{
                meal += "],";
            }
//            meal += "],";
            result += meal;
        }
        result += "]";
        return result;
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
        return bmi < 18.5D?
                "["+ "\"" +bmi+ "\"" +","+"\"" +"Underweight"+ "\"" + "]"
                :(18.5D <= bmi && bmi <= 24.9D?
                "["+ "\"" +bmi+ "\"" +","+"\"" +"Normal weight"+ "\"" + "]"

                :(25.0D <= bmi && bmi <= 29.9D?
                "["+ "\"" +bmi+ "\"" +","+"\"" +"Overweight"+ "\"" + "]"

                :(bmi >= 30.0D?
                "["+ "\"" +bmi+ "\"" +","+"\"" +"Obese"+ "\"" + "]"

                :"Value not found")));
    }
}
