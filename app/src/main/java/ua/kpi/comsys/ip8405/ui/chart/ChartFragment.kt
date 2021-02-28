package ua.kpi.comsys.ip8405.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.kpi.comsys.ip8405.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlin.math.PI
import kotlin.math.sin

class ChartFragment : Fragment() {

    private lateinit var chartViewModel: ChartViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chartViewModel =
                ViewModelProvider(this).get(ChartViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_charts, container, false)
        val graph = root.findViewById<LineChart>(R.id.lineChart)
        val pieChart = root.findViewById<PieChart>(R.id.pieChart)
        drawGraph(graph)
        drawPiechart(pieChart)
        if (pieChart.isVisible && graph.isVisible) pieChart.visibility = View.INVISIBLE
        val button = root.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        button.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when {
                checkedId == R.id.Graph && isChecked -> {
                    group.uncheck(R.id.Piechart)
                    graph.visibility = View.VISIBLE
                    pieChart.visibility = View.INVISIBLE
                }
                checkedId == R.id.Piechart && isChecked -> {
                    group.uncheck(R.id.Graph)
                    graph.visibility = View.INVISIBLE
                    pieChart.visibility = View.VISIBLE
                }
            }
        }

        return root
    }
    private fun drawGraph(graph: LineChart) {
        val points = ArrayList<Entry>()

        var i = (-2f * PI).toFloat()
        do {
            points.add(Entry(i, sin(i)))
            i += 0.2f
        } while((-2f * PI).toFloat() < i && i < (2f * PI).toFloat())
        points.add(Entry((2f * PI).toFloat(), sin((2f * PI).toFloat())))
        val line = LineDataSet(points, "y = sin(x)")
        line.setDrawValues(false)
        line.lineWidth = 3f
        line.color = ContextCompat.getColor(requireContext(), R.color.black)
        line.setDrawCircles(false)

        graph.xAxis.labelRotationAngle = 0f
        graph.axisLeft.axisMinimum = -2f
        graph.axisLeft.axisMaximum = 2f
        graph.axisRight.axisMinimum = -2f
        graph.axisRight.axisMaximum = 2f

        graph.data = LineData(line)
        graph.xAxis.axisMinimum = (-3f * PI).toFloat()
        graph.xAxis.axisMaximum = (3f * PI).toFloat()
        graph.description.isEnabled = false;
    }
    private fun drawPiechart(pieChart: PieChart) {
        val pieSlice = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()
        pieSlice.add(PieEntry(5f, "Brown"))
        colors.add(ContextCompat.getColor(requireContext(), R.color.brown))
        pieSlice.add(PieEntry(5f, "Sky Blue"))
        colors.add(ContextCompat.getColor(requireContext(), R.color.sky_blue))
        pieSlice.add(PieEntry(10f, "Orange"))
        colors.add(ContextCompat.getColor(requireContext(), R.color.orange))
        pieSlice.add(PieEntry(80f, "Dark Blue"))
        colors.add(ContextCompat.getColor(requireContext(), R.color.dark_blue))

        val pieDataSet = PieDataSet(pieSlice, "Pie slices colors")
        pieDataSet.colors = colors
        pieDataSet.valueTextSize = 8f
        pieDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        pieDataSet.selectionShift = 10f

        val pieData = PieData(pieDataSet)
        pieChart.description.isEnabled = false;
        pieChart.data = pieData
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.isDrawHoleEnabled = false;
        pieChart.setDrawEntryLabels(false)
    }
}