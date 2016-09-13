package com.drpweb.util;

import choco.Choco;
import choco.Options;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.kernel.solver.variables.integer.IntDomainVar;
import com.drpweb.disease.Disease;

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
                        ArrayList<int[]> carbohs, ArrayList<int[]> proteins, int bmr) {
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
            m.addConstraint(Choco.and(new Constraint[]{Choco.leq(Choco.sum(this.varKals[intVars]), bmr), Choco.geq(Choco.sum(this.varKals[intVars]), 1200)}));
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

    public Solver solvePatient (int num, int prd, int[] arr_id, int[] arr_kal, int[] arr_fat, int[] arr_carboh,
                                int[] arr_protein, ArrayList<int[]> kals, ArrayList<int[]> fats,
                                ArrayList<int[]> carbohs, ArrayList<int[]> proteins, Disease disease, int bmr){
        Model m = new CPModel();
        Solver s = new CPSolver();

        varIds = new IntegerVariable[num][3];
        varKals = new IntegerVariable[num][3];
        varFats = new IntegerVariable[num][3];
        varPros = new IntegerVariable[num][3];
        varCarboes = new IntegerVariable[num][3];



        for(int i = 0; i < num; i++){
            for(int j = 0; j < 3; j++){
                varIds[i][j] = Choco.makeIntVar("id_" + i + "_" + j,  arr_id,new String[0]);
                varKals[i][j] = Choco.makeIntVar("kal_" + i + "_" + j, arr_kal,new String[0]);
                varFats[i][j] = Choco.makeIntVar("fat_" + i + "_" + j, arr_fat,new String[0]);
                varPros[i][j] = Choco.makeIntVar("pro_" + i + "_" + j, arr_protein,new String[0]);
                varCarboes[i][j] = Choco.makeIntVar("carbo_" + i + "_" + j, arr_carboh,new String[0]);
            }
        }


        for(int i = 0; i < num; i++){
            for(int j = 0; j < 3; j++){
                m.addConstraint(Choco.feasPairAC(Options.C_EXT_AC32, this.varIds[i][j], this.varKals[i][j], kals));
                m.addConstraint(Choco.feasPairAC(Options.C_EXT_AC32, this.varIds[i][j], this.varFats[i][j], fats));
                m.addConstraint(Choco.feasPairAC(Options.C_EXT_AC32, this.varIds[i][j], this.varPros[i][j], proteins));
                m.addConstraint(Choco.feasPairAC(Options.C_EXT_AC32, this.varIds[i][j], this.varCarboes[i][j], carbohs));
            }
        }

        for (int i = 0; i < num; i++) {
            if(disease == null){
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(varKals[i]), bmr), Choco.geq(Choco.sum(varKals[i]), 1000)));
                m.addConstraint(Choco.leq(Choco.sum(varFats[i]), 33));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(varPros[i]), 60), Choco.geq(Choco.sum(varPros[i]), 45)));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(varCarboes[i]), 165), Choco.geq(Choco.sum(varCarboes[i]), 150)));
            }else{
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(varKals[i]), (int) (disease.getKcal()+500)), Choco.geq(Choco.sum(varKals[i]), (int) (disease.getKcal()-500))));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(varFats[i]), (int) (disease.getFat()+10)), Choco.geq(Choco.sum(varFats[i]), (int) (disease.getFat()-10))));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(varPros[i]), (int) (disease.getProt()+20)), Choco.geq(Choco.sum(varPros[i]), (int) (disease.getProt()-20))));
                m.addConstraint(Choco.and(Choco.leq(Choco.sum(varCarboes[i]), (int) (disease.getCarboh()+30)), Choco.geq(Choco.sum(varCarboes[i]), (int) (disease.getCarboh()-30))));
            }
        }

//       for(int i = 0; i < num; i++){
//            for(int j = 0; j < 3; j++){
//                if(fixVars.get(i+":"+j) != null){
//                    m.addConstraint(Choco.eq(varIds[i][j], (int)fixVars.get(i+":"+j)));
//                }
//            }
//        }

//        for (int i = 0; i < 3; i++) {
//            for (int j = i + 1; j < 3; j++) {
//                Constraint c = (Choco.neq(varIds[i / 3][i % 3], varIds[j / 3][j % 3]));
//                m.addConstraint(c);
//            }
//        }

        List<IntegerVariable> intVars = new ArrayList<>();
        int idx = 0;
        for(int i = 0; i < num; i++){
            for(int j = 0; j < 3; j++){
                intVars.add(varIds[i][j]);
            }
            idx++;
            if(idx % prd == 0){
                m.addConstraint(Choco.allDifferent(intVars.toArray(new IntegerVariable[intVars.size()])));
                intVars.clear();
            }

        }
        if(intVars.size() > 0){
            m.addConstraint(Choco.allDifferent(intVars.toArray(new IntegerVariable[intVars.size()])));
        }
//        for(int i = 0; i < num; i++){
//        	m.addConstraint(Choco.allDifferent(intVar));
//        }
        IntegerVariable[] lunch = new IntegerVariable[num];
        for(int i = 0; i < num; i++){
            lunch[i] = varIds[i][0];
        }
        m.addConstraint(Choco.allDifferent(lunch));
        IntegerVariable[] lunch2 = new IntegerVariable[num];
        for(int i = 0; i < num; i++){
            lunch2[i] = varIds[i][1];
        }
        m.addConstraint(Choco.allDifferent(lunch2));
        IntegerVariable[] lunch3 = new IntegerVariable[num];
        for(int i = 0; i < num; i++){
            lunch3[i] = varIds[i][2];
        }
        m.addConstraint(Choco.allDifferent(lunch3));
//
//        m.addConstraint(Choco.allDifferent(varIds[0][0], varIds[0][1], varIds[0][2]));
//        m.addConstraint(Choco.allDifferent(varIds[1][0], varIds[1][1], varIds[1][2]));
//        m.addConstraint(Choco.allDifferent(varIds[2][0], varIds[2][1], varIds[2][2]));
//        m.addConstraint(Choco.allDifferent(varIds[0][1], varIds[1][1], varIds[2][1]));



        s.read(m);
        // ChocoLogging.toSolution();
        s.setTimeLimit(20000);
        s.solve();
        // ChocoLogging.flushLogs();


        System.out.println("====================================================");
        for (int i = 0; i < num ; i++) {
            for(IntDomainVar var :s.getVar(varIds[i])){
                System.out.print(var+"       || ");
            }
            System.out.println();
            for(IntDomainVar var :s.getVar(varKals[i])){
                System.out.print(var+"    || ");
            }
            System.out.println();
            for(IntDomainVar var :s.getVar(varFats[i])){
                System.out.print(var+"     || ");
            }
            System.out.println();
            for(IntDomainVar var :s.getVar(varPros[i])){
                System.out.print(var+"     || ");
            }
            System.out.println();
            for(IntDomainVar var :s.getVar(varCarboes[i])){
                System.out.print(var+"  || ");
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
