/**
 * 
 */
package com.hanuritien.processor.detection.controller;

import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.hanuritien.processor.detection.coordinates.CoordinatesService;
import com.hanuritien.processor.detection.coordinates.CoordinatesVO;
import com.hanuritien.web.utils.DataTables.DataTablesInput;
import com.hanuritien.web.utils.DataTables.DataTablesOutput;
import com.hanuritien.web.utils.TimeOutAsyncTask.MyWebAsyncCallable;
import com.hanuritien.web.utils.TimeOutAsyncTask.MyWebAsyncTask;

/**
 * @author changu
 *
 */
@RestController
@RequestMapping("coordinates")
public class CoordinatesController {

	@Resource(name = "coordinatesService")
	CoordinatesService coordsvc;

	@RequestMapping(value = "getDataTables", method = RequestMethod.POST)
	public WebAsyncTask<DataTablesOutput<CoordinatesVO>> getDataTables()
			throws Exception {

		return new MyWebAsyncTask<DataTablesOutput<CoordinatesVO>>(5000l,
				new MyWebAsyncCallable<DataTablesOutput<CoordinatesVO>>(
						new Callable<DataTablesOutput<CoordinatesVO>>() {

							@Override
							public DataTablesOutput<CoordinatesVO> call() throws Exception {
								DataTablesInput input = new DataTablesInput(0, 0, 0, null, null, null, null);
								try {
									return coordsvc.getDatatables(input);
								} catch (Exception e) {
									return new DataTablesOutput<>(input.getDraw(), 0, 0, null, e.getMessage());
								}
							}
						}),
				new DataTablesOutput<CoordinatesVO>(0, 0, 0, null, "Timeout"));
	}

	@RequestMapping(value = "control", method = RequestMethod.POST)
	public WebAsyncTask<ResultVO<CoordinatesVO>> controll(@RequestBody final RequestListVO<CoordinatesVO> input) throws Exception {
		return new MyWebAsyncTask<ResultVO<CoordinatesVO>>(5000l,
				new MyWebAsyncCallable<>(new Callable<ResultVO<CoordinatesVO>>() {

					@Override
					public ResultVO<CoordinatesVO> call() throws Exception {
						ResultVO<CoordinatesVO> ret = new ResultVO<>(
								input.getDraw(), 
								0, 
								null, 
								null);

						try {
							if (input.getAction() == Action.Create || input.getAction() == Action.Update) {
								coordsvc.save(input.getData());
							}
							
							if (input.getAction() == Action.Delete) {
								coordsvc.delete(input.getData());
							}
							
							ret.setData(input.getData());
						} catch (Exception e) {
							ret.setResultCode(-1);
							ret.setResultMessage(e.getMessage());
						}

						return ret;
					}
				}), new ResultVO<CoordinatesVO>(input.getDraw(), -1, "timeout", null));
	}

}
